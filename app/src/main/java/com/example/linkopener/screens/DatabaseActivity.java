package com.example.linkopener.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.linkopener.data.DatabaseClient;
import com.example.linkopener.data.Pet;
import com.example.linkopener.R;
import com.example.linkopener.data.Ticket;
import com.example.linkopener.TicketInfo;
import com.example.linkopener.TicketsAdapter;
import com.example.linkopener.data.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DatabaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database);

        recyclerView = findViewById(R.id.rv_tickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatabaseActivity.this, AddTicketActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.add_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatabaseActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
        

        findViewById(R.id.add_pet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatabaseActivity.this, AddPetActivity.class);
                startActivity(intent);
            }
        });

        getTickets();

    }

    private void getTickets() {
        class GetTickets extends AsyncTask<Void, Void, List<TicketInfo>> {

            @Override
            protected List<TicketInfo> doInBackground(Void... voids) {

                List<TicketInfo> res = new ArrayList<TicketInfo>();
                Map<Ticket, Pet> ticketPetUserMap = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .ticketDao()
                        .getAllWithPet();

                Map<Pet, User> petUserMap = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .petDao()
                        .getAllWithUser();

                for (Iterator<Ticket> i = ticketPetUserMap.keySet().iterator(); i.hasNext();) {
                    Ticket ticket = i.next();
                    TicketInfo ticketInfo = new TicketInfo();
                    ticketInfo.setNumber(ticket.getTId().toString());
                    ticketInfo.setPet(ticketPetUserMap.get(ticket).getNickname());
                    ticketInfo.setOwner("Not found");
                    for(Pet p: petUserMap.keySet()) {
                        User u = petUserMap.get(p);
                        if (Objects.equals(ticket.getPetId(), p.getPId())) {
                            ticketInfo.setOwner(u.getFirstName() + " " + u.getLastName());
                        }
                    }
                    ticketInfo.setDate(ticket.getDate());
                    ticketInfo.setChecked(ticket.getChecked());
                    res.add(ticketInfo);
                }
                return res;
            }

            @Override
            protected void onPostExecute(List<TicketInfo> tickets) {
                super.onPostExecute(tickets);
                TicketsAdapter adapter = new TicketsAdapter(DatabaseActivity.this, tickets);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTickets gt = new GetTickets();
        gt.execute();
    }
}
