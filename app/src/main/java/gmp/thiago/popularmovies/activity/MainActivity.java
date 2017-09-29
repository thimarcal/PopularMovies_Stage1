package gmp.thiago.popularmovies.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

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

        loadMovies();
    }

    private void loadMovies() {
        // TODO: Read type of search from Shared Preferences
        new FetchMoviesData().execute(NetworkUtils.SEARCH_BY_POPULAR);
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
