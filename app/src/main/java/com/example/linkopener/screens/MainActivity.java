package com.example.linkopener.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkopener.R;

public class MainActivity extends AppCompatActivity {

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

    public static final String MSG_KEY = "link_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEnterLinkBtn();
        initOpenLinkBtn();
        initDatabaseBtn();
    }

    protected void onResume () {
        super.onResume();

        String linkText = getIntentText();
        Button openLink = findViewById(R.id.open_link);

        if (linkText == null) {
            openLink.setActivated(false);
        } else {
            setLinkText(linkText);
            openLink.setActivated(true);
        }
    }

    private void initOpenLinkBtn () {
        Button openLink = findViewById(R.id.open_link);

        openLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW);
                TextView linkText = findViewById(R.id.link_text);
                intent.setData(Uri.parse(linkText.getText().toString()));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void initEnterLinkBtn () {
        Button enterLink = findViewById(R.id.enter_link);

        enterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, InputLinkActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void initDatabaseBtn () {
        Button database = findViewById(R.id.go_database);

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void setLinkText (String text) {
        TextView linkText = findViewById(R.id.link_text);
        linkText.setText(text);
    }

    private String getIntentText () {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.MSG_KEY);
    }
}