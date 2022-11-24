package com.example.linkopener.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linkopener.R;

public class InputLinkActivity extends AppCompatActivity {

    private int previousOrientation = Configuration.ORIENTATION_PORTRAIT;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation != previousOrientation) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Toast.makeText(this, getResources().getString(R.string.toast_landscape), Toast.LENGTH_SHORT).show();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Toast.makeText(this, getResources().getString(R.string.toast_portrait), Toast.LENGTH_SHORT).show();
            }
        }

        previousOrientation = newConfig.orientation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_link);
        initBtn();
    }

    private void initBtn() {
        EditText link = findViewById(R.id.editText);

        Button acceptBtn = findViewById(R.id.accept_button);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link_text = link.getText().toString();
                if (link_text.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty link", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(InputLinkActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.MSG_KEY, link_text);
                    startActivity(intent);
                }
            }
        });
    }
}