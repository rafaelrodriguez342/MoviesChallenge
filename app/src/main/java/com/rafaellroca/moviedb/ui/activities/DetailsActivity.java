package com.rafaellroca.moviedb.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaellroca.moviedb.R;
import com.rafaellroca.moviedb.models.VideoData;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private static final String VIDEO_EXTRA_KEY = "videoExtraKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);




        ImageView videoImage = findViewById(R.id.main_image);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView titleTextView = findViewById(R.id.title);
        TextView voteCountTextView = findViewById(R.id.vote_count);
        TextView voteAverageTextView = findViewById(R.id.vote_average);


        Slide slideIn = new Slide();
        View decor = getWindow().getDecorView();
        slideIn.addTarget(descriptionTextView);
        slideIn.addTarget(voteCountTextView);
        slideIn.addTarget(voteAverageTextView);
        slideIn.addTarget(findViewById(R.id.vote_average_label))
               .addTarget(R.id.vote_count_label);

        slideIn.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        slideIn.excludeTarget(android.R.id.statusBarBackground, true);
        slideIn.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(slideIn);
        getWindow().setExitTransition(slideIn);

        VideoData videoData = getIntent().getParcelableExtra(VIDEO_EXTRA_KEY);

        Picasso.get().load(videoData.getImagePath())
               .noFade()
               .error(R.drawable.img_place_holder)
               .centerCrop()
               .fit()
               .into(videoImage);

        descriptionTextView.setText(videoData.getDescription());
        titleTextView.setText(videoData.getTitle());
        voteCountTextView.setText(videoData.getVoteCount());
        voteAverageTextView.setText(videoData.getVoteAverage());

    }

    private void initFromVideo(VideoData videoData){

    }

    public static Intent createNewIntent(Context context, VideoData video) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(VIDEO_EXTRA_KEY, video);
        return intent;
    }
}
