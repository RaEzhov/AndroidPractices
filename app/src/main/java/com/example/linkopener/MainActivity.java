package com.example.linkopener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_KEY = "link_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEnterLinkBtn();
        initOpenLinkBtn();

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

    private void setLinkText (String text) {
        TextView linkText = findViewById(R.id.link_text);
        linkText.setText(text);
    }

    private String getIntentText () {
        Intent intent = getIntent();
        return intent.getStringExtra(MainActivity.MSG_KEY);
    }
}