package com.example.linkopener.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.linkopener.data.DatabaseClient;
import com.example.linkopener.data.Pet;
import com.example.linkopener.R;
import com.example.linkopener.data.Ticket;
import com.example.linkopener.data.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddTicketActivity extends AppCompatActivity {
    
    private Integer selectedUserId, selectedPetId;
    private Spinner chooseOwner, choosePet;
    private EditText enterDate;

    private ArrayAdapter<String> usersAdapter, petsAdapter;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        selectedUserId = -1;
        selectedPetId = -1;

        chooseOwner = findViewById(R.id.chooseOwner);
        choosePet = findViewById(R.id.choosePet);
        enterDate = findViewById(R.id.date);

        findViewById(R.id.add_ticket_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPetId == -1 || selectedUserId == -1) {
                    Toast.makeText(getApplicationContext(), "Choose pet", Toast.LENGTH_LONG).show();
                    return;
                }
                
                saveTicket();
            }
        });

        chooseOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUserId = Integer.parseInt(parentView.getItemAtPosition(position).toString().split(" ")[0]);
                Toast.makeText(getApplicationContext(), selectedUserId.toString(), Toast.LENGTH_LONG).show();
                loadPets();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedUserId = -1;
            }
        });

        choosePet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPetId = Integer.parseInt(parentView.getItemAtPosition(position).toString().split(" ")[0]);
                Toast.makeText(getApplicationContext(), selectedPetId.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedPetId = -1;
            }
        });
        loadUsers();
    }

    private void loadUsers() {
        class LoadUsers extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .userDao()
                        .getAll();

                List<String> usersNames = new ArrayList<String>();

                for (Iterator<User> i = userList.iterator(); i.hasNext(); ) {
                    User u = i.next();
                    usersNames.add(u.getUId().toString() + " " + u.getFirstName() + " " + u.getLastName());
                }
                return usersNames;
            }

            @Override
            protected void onPostExecute(List<String> users) {
                super.onPostExecute(users);
                usersAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, users);
                usersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                chooseOwner.setAdapter(usersAdapter);
            }
        }

        LoadUsers lu = new LoadUsers();
        lu.execute();
    }

    private void loadPets() {
        class LoadPets extends AsyncTask<Void, Void, List<String>> {

            @Override
            protected List<String> doInBackground(Void... voids) {
                List<Pet> petList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .petDao()
                        .getAllByOwnerId(selectedUserId);

               List<String> petsNicknames = new ArrayList<String>();

                for (Iterator<Pet> i = petList.iterator(); i.hasNext(); ) {
                    Pet p = i.next();
                    petsNicknames.add(p.getPId().toString() + " " + p.getNickname());
                }
                return petsNicknames;
            }

            @Override
            protected void onPostExecute(List<String> pets) {
                super.onPostExecute(pets);
                petsAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, pets);
                petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                choosePet.setAdapter(petsAdapter);
            }
        }

        LoadPets lp = new LoadPets();
        lp.execute();
    }


    private void saveTicket() {
        class SaveTicket extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Ticket ticket = new Ticket();
                ticket.setDate(enterDate.getText().toString());
                ticket.setPetId(selectedPetId);
                ticket.setChecked(false);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().ticketDao().insert(ticket);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTicket st = new SaveTicket();
        st.execute();
    }
}