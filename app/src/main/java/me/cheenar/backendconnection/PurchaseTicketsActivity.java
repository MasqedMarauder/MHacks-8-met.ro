package me.cheenar.backendconnection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PurchaseTicketsActivity extends AppCompatActivity {

    private Spinner passTypeSpinner;
    private String passTypeString = null;

    private CalendarView startDateCalendarView;
    private String startDateString;
    private TextView validDatesTextView;

    private Button continueToPurchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_tickets);

        continueToPurchaseButton = (Button)findViewById(R.id.continueToPurchaseButton);
        continueToPurchaseButton.setEnabled(false);

        passTypeSpinner = (Spinner)findViewById(R.id.passTypeSpinner);
        ArrayAdapter<CharSequence> passTypeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pass_types_array, android.R.layout.simple_spinner_item);
        passTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        passTypeSpinner.setAdapter(passTypeSpinnerAdapter);
        passTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    passTypeString = null;
                    continueToPurchaseButton.setEnabled(false);
                    //Toast.makeText(PurchaseTicketsActivity.this, "No Option Selected!", Toast.LENGTH_SHORT).show();
                } else {
                    continueToPurchaseButton.setEnabled(true);
                    passTypeString = getResources().getStringArray(R.array.pass_types_array)[position];
                    //Toast.makeText(PurchaseTicketsActivity.this, "Selected Option: " + passTypeString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                passTypeString = null;
                Toast.makeText(PurchaseTicketsActivity.this, "No Option Selected!", Toast.LENGTH_SHORT).show();
            }
        });

        validDatesTextView = (TextView)findViewById(R.id.validDatesTextView);

        startDateString = (new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US)).format(new Date(System.currentTimeMillis()));
        validDatesTextView.setText(startDateString);

        startDateCalendarView = (CalendarView)findViewById(R.id.startDateCalendarView);
        startDateCalendarView.setMinDate(System.currentTimeMillis());
        startDateCalendarView.setDate(System.currentTimeMillis());
        startDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String inputString = "" + year + " " + (month + 1) + " " + dayOfMonth;
                DateFormat df = new SimpleDateFormat("yy MM dd", Locale.US);
                Date date = null;
                try {
                    date = df.parse(inputString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy", Locale.US);
                startDateString = dateFormat.format(date);
                validDatesTextView.setText(startDateString);
            }
        });
    }

    public void continueToPurchaseButtonClicked(View v) {
        Intent i = new Intent(this, EnterPaymentInfoActivity.class);
        i.putExtra("passType", passTypeString);
        i.putExtra("startDate", startDateString);
        startActivity(i);
    }

}
