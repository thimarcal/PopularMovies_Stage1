package gmp.thiago.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiagom on 9/28/17.
 */

public class MovieJson {


    /**
     * JSON Object for the response from TheMovieDB
     * It has some information about the response itself, and a List of Movie Objects returned
     */

    private int page;
    private int total_results;
    private int total_pages;
    private List<Movie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public static class Movie implements Parcelable {
        /**
         * The Movie Object returned from TheMovieDB request.
         */

        private int vote_count;
        private int id;
        private boolean video;
        private float vote_average;
        private String title;
        private double popularity;
        private String poster_path;
        private String original_language;
        private String original_title;
        private String backdrop_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private List<Integer> genre_ids;

        public Movie(Parcel in) {
            vote_count = in.readInt();
            id = in.readInt();
            video = in.readByte() != 0;
            vote_average = in.readFloat();
            title = in.readString();
            popularity = in.readDouble();
            poster_path = in.readString();
            original_language = in.readString();
            original_title = in.readString();
            backdrop_path = in.readString();
            adult = in.readByte() != 0;
            overview = in.readString();
            release_date = in.readString();
            genre_ids = new ArrayList<>();
            in.readList(genre_ids, null);
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(vote_count);
            parcel.writeInt(id);
            parcel.writeByte((byte)(video ? 1 : 0));
            parcel.writeFloat(vote_average);
            parcel.writeString(title);
            parcel.writeDouble(popularity);
            parcel.writeString(poster_path);
            parcel.writeString(original_language);
            parcel.writeString(original_title);
            parcel.writeString(backdrop_path);
            parcel.writeByte((byte)(adult ? 1 : 0));
            parcel.writeString(overview);
            parcel.writeString(release_date);
            parcel.writeArray(genre_ids.toArray());

        }

        public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };
    }
}
