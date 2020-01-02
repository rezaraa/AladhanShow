package com.rezara.aladhanshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rezara.aladhanshow.Const;
import com.rezara.aladhanshow.R;
import com.rezara.aladhanshow.db.SqliteDbHelper;
import com.rezara.aladhanshow.pojo.MovieDetailsResult;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends AppCompatActivity {
    Intent intent;
    ImageView imageMoviePoster, imgDownloadDetails;
    TextView txtMovieTitle, txtMovieBudget, txtMovieGenres, txtMovieRate, txtMovieDesc;
    SqliteDbHelper dbHelper;
    MovieDetailsResult result;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        intent = getIntent();
        initViews();
        if (intent.getIntExtra(Const.TAG_ITEM_ID, 0) != 0) {
            ID = intent.getIntExtra(Const.TAG_ITEM_ID, 0);
            if (dbHelper.getMovieDetail(ID))
                imgDownloadDetails.setImageResource(R.drawable.ic_ok);
            connectToInternet(ID);
        }
        click();
    }

    private void connectToInternet(int ID) {
        AsyncHttpClient client = new AsyncHttpClient();


        String address = "https://api.themoviedb.org/3/movie/" + ID
                + "?api_key=" + Const.API_KEY + "&language=en-US";


        client.get(address, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("Response", response.toString());
                Gson gson = new Gson();
                result = gson.fromJson(response.toString(), MovieDetailsResult.class);
                setViewValue(result);
                imgDownloadDetails.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });
    }


    private void setViewValue(MovieDetailsResult result) {
        Glide.with(this).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"
                + result.getPoster_path())
                .into(imageMoviePoster).clearOnDetach();
        txtMovieTitle.setText(result.getTitle());
        txtMovieBudget.setText(String.valueOf(result.getBudget()));
        txtMovieRate.setText(String.valueOf(result.getVote_average()));
        txtMovieDesc.setText(result.getOverview());
    }

    private void initViews() {
        imageMoviePoster = findViewById(R.id.imageMoviePoster);
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        txtMovieBudget = findViewById(R.id.txtMovieBudget);
        txtMovieGenres = findViewById(R.id.txtMovieGenres);
        txtMovieRate = findViewById(R.id.txtMovieRate);
        txtMovieDesc = findViewById(R.id.txtMovieDesc);
        imgDownloadDetails = findViewById(R.id.imgDownloadDetails);
        dbHelper = new SqliteDbHelper(this, "Movie", null, 1);
    }


    public void click() {
        imgDownloadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result != null) {
                    if (!dbHelper.getMovieDetail(result.getId())) {
                        dbHelper.inserIntoTable(result.getId(), result.getTitle());
                        imgDownloadDetails.setImageResource(R.drawable.ic_ok);
                    } else {
                        dbHelper.deleteFromTable(result.getId());
                        imgDownloadDetails.setImageResource(R.drawable.ic_download_button);
                    }
                }
            }
        });


    }
}
