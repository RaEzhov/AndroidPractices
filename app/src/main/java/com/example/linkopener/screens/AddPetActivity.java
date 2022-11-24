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
import com.example.linkopener.data.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddPetActivity extends AppCompatActivity {

    private Spinner chooseOwner;
    private Integer selectedUserId;
    private ArrayAdapter<String> usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        selectedUserId = -1;
        chooseOwner = findViewById(R.id.chooseOwner);

        chooseOwner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUserId = Integer.parseInt(parentView.getItemAtPosition(position).toString().split(" ")[0]);
                Toast.makeText(getApplicationContext(), selectedUserId.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedUserId = -1;
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePet();
            }
        });
        
        loadUsers();
    }

    private void savePet() {
        String species = ((EditText)findViewById(R.id.species)).getText().toString().trim();
        String nickname = ((EditText)findViewById(R.id.nickname)).getText().toString().trim();

        if (selectedUserId == -1) {
            Toast.makeText(getApplicationContext(), "Owner required", Toast.LENGTH_LONG).show();
            return;
        }

        if (species.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Species required", Toast.LENGTH_LONG).show();
            return;
        }

        if (nickname.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nickname required", Toast.LENGTH_LONG).show();
            return;
        }


        class SavePet extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Pet pet = new Pet();
                pet.setSpecies(species);
                pet.setNickname(nickname);
                pet.setOwnerId(selectedUserId);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().petDao().insert(pet);
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

        SavePet sp = new SavePet();
        sp.execute();
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
}