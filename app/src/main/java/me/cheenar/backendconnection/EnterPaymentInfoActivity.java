package me.cheenar.backendconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnterPaymentInfoActivity extends AppCompatActivity {

    private TextView paymentReviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_payment_info);

        Bundle ticketInfo = getIntent().getExtras();
        String passType = ticketInfo.getString("passType");
        String startDate = ticketInfo.getString("startDate");
        Toast.makeText(this, startDate, Toast.LENGTH_SHORT).show();
        paymentReviewTextView = (TextView)findViewById(R.id.paymentReviewTextView);
        paymentReviewTextView.setText("Pass Type: " + passType + "\n" + "Start Date: " + startDate);

        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy");
        try
        {
            Date start = sdf.parse(startDate);
            long time = start.getTime();

            if(passType == "1-Day Pass")
            {
            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void purchaseTicketButtonClicked(View v) {
        Toast.makeText(EnterPaymentInfoActivity.this, "Purchase Complete!", Toast.LENGTH_LONG).show();



        Intent i = new Intent(this, PortalActivity.class);
        startActivity(i);
    }

}
