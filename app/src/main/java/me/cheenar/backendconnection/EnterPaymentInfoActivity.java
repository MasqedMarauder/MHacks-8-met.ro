package me.cheenar.backendconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.password;

public class EnterPaymentInfoActivity extends AppCompatActivity {


    String startDate;
    String endDate;
    String passType;

    private TextView paymentReviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_payment_info);

        SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy");
        Bundle ticketInfo = getIntent().getExtras();
        passType = ticketInfo.getString("passType");
        startDate = ticketInfo.getString("startDate");
        paymentReviewTextView = (TextView)findViewById(R.id.paymentReviewTextView);
        paymentReviewTextView.setText("Pass Type: " + passType + "\n" + "Start Date: " + startDate);
        endDate = "";
        try
        {
            Date start = sdf.parse(startDate);
            long time = start.getTime();
            long add = -1;

            if(passType.equals("1-Day Pass"))
            {
                add = 86400000;
            }
            if(passType.equals("3-Day Pass"))
            {
                add = (86400000 * 2);
            }
            if(passType.equals("Week Pass"))
            {
                add = (86400000 * 7);
            }
            if(passType.equals("Month Pass"))
            {
                add = (86400000 * 30);
            }
            if(passType.equals("Annual Pass"))
            {
                add = (86400000 * 365);
            }

            time += add;

            Date nue = new Date(time);
            endDate = sdf.format(nue).toString();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void purchaseTicketButtonClicked(View v) {
        //Toast.makeText(EnterPaymentInfoActivity.this, "Purchase Complete!", Toast.LENGTH_LONG).show();

        String s = "http://66.175.213.218/p/purchase_fare?profile_id=" + MainActivity.user.profileID + "&fare_type=" + passType.replace(" ", "%20") + "&active_start=" + startDate + "&active_end=" + endDate;
        try
        {
            URL url = new URL(s);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            Toast.makeText(this, "Successfully Purchased Ticket", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Intent i = new Intent(this, PortalActivity.class);
        startActivity(i);
    }

}
