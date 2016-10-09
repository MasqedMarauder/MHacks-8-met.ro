package me.cheenar.backendconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PortalActivity extends AppCompatActivity {

    private Button purchaseTickets;
    private Button viewTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        purchaseTickets = (Button)findViewById(R.id.purchaseFare);
        viewTickets = (Button)findViewById(R.id.viewTickets);

        purchaseTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PurchaseTicketsActivity.class);
                startActivity(intent);
            }
        });

        viewTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FaresActivity.class);
                startActivity(intent);
            }
        });
    }
}
