package com.example.linkopener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MicrophoneDirection;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputLinkActivity extends AppCompatActivity {

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