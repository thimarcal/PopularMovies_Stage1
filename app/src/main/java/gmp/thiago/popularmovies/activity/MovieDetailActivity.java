package gmp.thiago.popularmovies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import gmp.thiago.popularmovies.R;
import gmp.thiago.popularmovies.adapter.MovieAdapter;
import gmp.thiago.popularmovies.data.MovieJson;
import gmp.thiago.popularmovies.utilities.NetworkUtils;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView movieThumbnail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        Intent retrievedIntent = getIntent();
        MovieJson.Movie movie = null;
        if (null != retrievedIntent && retrievedIntent.hasExtra("movie")) {
            movie = retrievedIntent.getParcelableExtra("movie");

            Log.d("Thiago", ""+movie.getTitle());
        }

        if (null != movie) {
            movieThumbnail = (ImageView) findViewById(R.id.movie_detail_imageview);
            String thumbnailPath = MovieAdapter.IMAGE_BASE_URL+"w185"+movie.getPoster_path();
            Picasso.with(this).load(thumbnailPath).placeholder(R.drawable.ic_image).into(movieThumbnail);
        }
    }
}
