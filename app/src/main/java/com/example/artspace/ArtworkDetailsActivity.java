package com.example.artspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ArtworkDetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            ArtworkDetailsActivity.class.getSimpleName();

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        // Get the Intent that activated this Activity, then the extras.
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        String artist = intent.getStringExtra(MainActivity.EXTRA_ARTIST);
        String year = intent.getStringExtra(MainActivity.EXTRA_YEAR);
        mUrl = intent.getStringExtra(MainActivity.EXTRA_URL);

        TextView titleTextView = findViewById(R.id.text_details_title);
        TextView artistTextView = findViewById(R.id.text_details_artist);
        TextView yearTextView = findViewById(R.id.text_details_year);

        titleTextView.setText("Title: " + title);
        artistTextView.setText("Artist: " + artist);
        yearTextView.setText("Year: " + year);
    }

    /**
     * Opens the artwork's web page in the browser using an implicit Intent.
     */
    public void visitWebsite(View view) {
        Uri webpage = Uri.parse(mUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can't handle this intent!");
            Toast.makeText(this, R.string.no_browser_message,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Returns to the main Activity.
     */
    public void goBack(View view) {
        finish();
    }
}
