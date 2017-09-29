package gmp.thiago.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gmp.thiago.popularmovies.R;
import gmp.thiago.popularmovies.data.MovieJson;

/**
 * Created by thiagom on 9/28/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private static final String IMAGE_BASE_URL = " http://image.tmdb.org/t/p/";
    private List<MovieJson.Movie> mMovies = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        String posterPath = mMovies.get(position).getPoster_path();
        posterPath = IMAGE_BASE_URL+"w185/"+posterPath;
        Log.d("Thiago", "Position: "+ position+" - "+posterPath);
        Picasso.with(mContext).load(posterPath).fit().placeholder(R.drawable.ic_image).into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(List movies) {
        mMovies = movies;
        Log.d("Thiago", ""+movies.size());
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView)itemView.findViewById(R.id.imageview_movie_poster);
        }


    }
}
