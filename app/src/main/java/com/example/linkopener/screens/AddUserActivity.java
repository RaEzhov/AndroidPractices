package com.example.linkopener.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linkopener.data.DatabaseClient;
import com.example.linkopener.R;
import com.example.linkopener.data.User;

public class AddUserActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DatabaseActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

    private void saveUser() {
        String firstName = ((EditText)findViewById(R.id.first_name)).getText().toString().trim();
        String lastName = ((EditText)findViewById(R.id.last_name)).getText().toString().trim();

        if (firstName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "First name required", Toast.LENGTH_LONG).show();
            return;
        }

        if (lastName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Last name required", Toast.LENGTH_LONG).show();
            return;
        }


        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().insert(user);
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
        
        SaveUser su = new SaveUser();
        su.execute();
    }
}