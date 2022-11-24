package com.example.linkopener.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkopener.data.DatabaseClient;
import com.example.linkopener.R;
import com.example.linkopener.data.Ticket;
import com.example.linkopener.TicketInfo;

public class CheckTicketActivity extends AppCompatActivity {
    
    private Switch ticketChecked;
    private TextView ticketNumber, ticketOwner, ticketPet, ticketDate;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ticket);

        ticketNumber = findViewById(R.id.ticket_number);
        ticketOwner = findViewById(R.id.ticket_owner);
        ticketPet = findViewById(R.id.ticket_pet);
        ticketDate = findViewById(R.id.ticket_date);
        ticketChecked = findViewById(R.id.ticket_checked);

        final TicketInfo ticket = (TicketInfo) getIntent().getSerializableExtra("ticket");

        loadTicket(ticket);

        findViewById(R.id.ticket_checked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTicket(ticket);
            }
        });

        findViewById(R.id.delete_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CheckTicketActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTicket(ticket);
                    }
                });
                
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }


    private void loadTicket(TicketInfo ticket) {
       ticketNumber.setText(ticket.getNumber());
       ticketOwner.setText(ticket.getOwner());
       ticketPet.setText(ticket.getPet());
       ticketDate.setText(ticket.getDate());
       ticketChecked.setChecked(ticket.getChecked());
    }

    private void checkTicket(TicketInfo ticket) {
        class UpdateTicket extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Ticket t = DatabaseClient.getInstance(getApplicationContext())
                            .getAppDatabase()
                            .ticketDao()
                            .getById(ticket.getNumber());
                    t.setChecked(!t.getChecked());

                    DatabaseClient.getInstance(getApplicationContext())
                            .getAppDatabase()
                            .ticketDao()
                            .update(t);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isOk) {
                super.onPostExecute(isOk);
                String toast = isOk ? "Updated" : "Error";
                Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
            }
        }

        UpdateTicket ut = new UpdateTicket();
        ut.execute();
    }


    private void deleteTicket(TicketInfo ticket) {
        class DeleteTicket extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Ticket t = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .ticketDao()
                        .getById(ticket.getNumber());

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .ticketDao()
                        .delete(t);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(CheckTicketActivity.this, DatabaseActivity.class));
            }
        }

        DeleteTicket dt = new DeleteTicket();
        dt.execute();
    }
}