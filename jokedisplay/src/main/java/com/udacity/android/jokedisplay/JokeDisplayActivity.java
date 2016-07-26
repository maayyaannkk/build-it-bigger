package com.udacity.android.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView jokeView = (TextView)findViewById(R.id.textViewJoke);
        String joke = getIntent().getStringExtra("joke");
        jokeView.setText(joke);
    }
}
