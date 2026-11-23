package com.example.artspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Keys for the Intent extras (sent to ArtworkDetailsActivity)
    public static final String EXTRA_TITLE =
            "com.example.artspace.extra.TITLE";
    public static final String EXTRA_ARTIST =
            "com.example.artspace.extra.ARTIST";
    public static final String EXTRA_YEAR =
            "com.example.artspace.extra.YEAR";
    public static final String EXTRA_URL =
            "com.example.artspace.extra.URL";

    // Key used to save the current position in the state Bundle
    private static final String STATE_INDEX = "current_index";

    // Artwork data: the same position in every array belongs to one artwork
    private int[] mImages = {
            R.drawable.artwork_1, R.drawable.artwork_2, R.drawable.artwork_3,
            R.drawable.artwork_4, R.drawable.artwork_5, R.drawable.artwork_6};
    private int[] mTitles = {
            R.string.title_1, R.string.title_2, R.string.title_3,
            R.string.title_4, R.string.title_5, R.string.title_6};
    private int[] mArtists = {
            R.string.artist_1, R.string.artist_2, R.string.artist_3,
            R.string.artist_4, R.string.artist_5, R.string.artist_6};
    private int[] mYears = {
            R.string.year_1, R.string.year_2, R.string.year_3,
            R.string.year_4, R.string.year_5, R.string.year_6};
    private String[] mUrls = {
            "https://en.wikipedia.org/wiki/Mona_Lisa",
            "https://en.wikipedia.org/wiki/The_Starry_Night",
            "https://en.wikipedia.org/wiki/The_Scream",
            "https://en.wikipedia.org/wiki/Girl_with_a_Pearl_Earring",
            "https://en.wikipedia.org/wiki/The_Persistence_of_Memory",
            "https://en.wikipedia.org/wiki/The_Great_Wave_off_Kanagawa"};

    private int mCurrentIndex = 0;

    private TextView mCounterTextView;
    private ImageView mArtworkImageView;
    private TextView mTitleTextView;
    private TextView mArtistYearTextView;
    private Button mPreviousButton;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all the view variables.
        mCounterTextView = findViewById(R.id.text_counter);
        mArtworkImageView = findViewById(R.id.image_artwork);
        mTitleTextView = findViewById(R.id.text_title);
        mArtistYearTextView = findViewById(R.id.text_artist_year);
        mPreviousButton = findViewById(R.id.button_previous);
        mNextButton = findViewById(R.id.button_next);

        // Restore the state.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(STATE_INDEX, 0);
            Log.d(LOG_TAG, "State restored, index = " + mCurrentIndex);
        }

        showArtwork();
    }

    /**
     * Saves the current artwork position before the Activity is destroyed
     * (for example when the screen is rotated).
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_INDEX, mCurrentIndex);
        Log.d(LOG_TAG, "State saved, index = " + mCurrentIndex);
    }

    /**
     * Shows the next artwork. Called from android:onClick of the Next button.
     */
    public void showNext(View view) {
        if (mCurrentIndex < mImages.length - 1) {
            mCurrentIndex++;
            showArtwork();
        }
    }

    /**
     * Shows the previous artwork. Called from android:onClick of the
     * Previous button.
     */
    public void showPrevious(View view) {
        if (mCurrentIndex > 0) {
            mCurrentIndex--;
            showArtwork();
        }
    }

    /**
     * Opens ArtworkDetailsActivity with an explicit Intent when the
     * artwork image is clicked.
     */
    public void openDetails(View view) {
        Log.d(LOG_TAG, "Image clicked!");
        Intent intent = new Intent(this, ArtworkDetailsActivity.class);
        intent.putExtra(EXTRA_TITLE, getString(mTitles[mCurrentIndex]));
        intent.putExtra(EXTRA_ARTIST, getString(mArtists[mCurrentIndex]));
        intent.putExtra(EXTRA_YEAR, getString(mYears[mCurrentIndex]));
        intent.putExtra(EXTRA_URL, mUrls[mCurrentIndex]);
        startActivity(intent);
    }

    /**
     * Updates the counter, image, texts and the state of both buttons
     * to match the current artwork.
     */
    private void showArtwork() {
        mCounterTextView.setText((mCurrentIndex + 1) + " / " + mImages.length);
        mArtworkImageView.setImageResource(mImages[mCurrentIndex]);
        mTitleTextView.setText(mTitles[mCurrentIndex]);
        mArtistYearTextView.setText(getString(mArtists[mCurrentIndex])
                + " (" + getString(mYears[mCurrentIndex]) + ")");

        // Disable Previous on the first artwork, Next on the last one.
        mPreviousButton.setEnabled(mCurrentIndex > 0);
        mNextButton.setEnabled(mCurrentIndex < mImages.length - 1);
    }
}
