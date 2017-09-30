package gmp.thiago.popularmovies.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gmp.thiago.popularmovies.R;
import gmp.thiago.popularmovies.adapter.MovieAdapter;
import gmp.thiago.popularmovies.data.MovieJson;
import gmp.thiago.popularmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener{

    private RecyclerView mMoviesRV;
    private GridLayoutManager layoutManager;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Retrieve the recyclerview in which we'l hold our movies. With that, we can set the
         * Adapter and Layout Manager.
         */
        mMoviesRV = (RecyclerView)findViewById(R.id.recyclerview_movies);

        layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mMoviesRV.setLayoutManager(layoutManager);

        mMoviesRV.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getApplicationContext(), this);
        mMoviesRV.setAdapter(mMovieAdapter);

        // If restoring state, we shall consider saved values, otherwise, we load the movies
        if (null != savedInstanceState &&
                    savedInstanceState.containsKey(getString(R.string.movies_key))) {
            ArrayList movies = savedInstanceState.getParcelableArrayList(getString(R.string.movies_key));
            mMovieAdapter.setMovies(movies);
        } else {
            loadMovies();
        }
    }

    private void loadMovies() {
        // TODO: Read type of search from Shared Preferences
        Log.d("Thiago", "insideLoadMovies");
        new FetchMoviesData().execute(NetworkUtils.SEARCH_BY_POPULAR);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save state by putting the movies inside the bundle
        outState.putParcelableArrayList(getString(R.string.movies_key), mMovieAdapter.getMovies());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if (null != menuInflater) {
            menuInflater.inflate(R.menu.main_menu, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.sort_by == item.getItemId()) {
            //TODO: Here we'll change the settings Later

            Intent intent = new Intent(this, MovieDetailActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(MovieJson.Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);

        startActivity(intent);
    }


    /**
     * AsyncTask for Fetching Movies data from TheMoviesDB
     */
    public class FetchMoviesData extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {

            String jsonMoviesResponse = null;
            // If there's no search type, there's nothing we can do here
            if(params.length == 0) {
                return null;
            }

            int searchType = params[0];
            URL moviesUrl = NetworkUtils.buildUrl(searchType);

            try {
                jsonMoviesResponse = NetworkUtils.getHttpResponse(moviesUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonMoviesResponse;
        }

        @Override
        protected void onPostExecute(String response) {
            // Here, we'll transform the JSON into a MovieJson Object using Gson
            Gson gson = new Gson();
            MovieJson jsonObject = gson.fromJson(response, MovieJson.class);

            if (null != mMovieAdapter) {
                mMovieAdapter.setMovies(jsonObject.getResults());
            }
        }
    }
}
