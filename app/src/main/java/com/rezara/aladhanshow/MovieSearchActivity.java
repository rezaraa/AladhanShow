package com.rezara.aladhanshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rezara.aladhanshow.Const;
import com.rezara.aladhanshow.R;
import com.rezara.aladhanshow.pojo.SearchResult;
import com.rezara.aladhanshow.movie.recycler.MovieSearchItemAdapter;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieSearchActivity extends AppCompatActivity implements View.OnClickListener, MovieSearchItemAdapter.AdapterItemClicked {
    EditText edtSearch;
    ImageView imageSearch;
    RecyclerView searchRecycler;
    MovieSearchItemAdapter adapter;
    SearchResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedatabase);
        initViews();

    }

    private void initViews() {
        edtSearch = findViewById(R.id.edtSearch);
        imageSearch = findViewById(R.id.imageSearch);
        searchRecycler = findViewById(R.id.searchRecycler);
        imageSearch.setOnClickListener(this);
        adapter = new MovieSearchItemAdapter();
        searchRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchRecycler.setAdapter(adapter);
        adapter.setAdapterItemClicked(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageSearch: {
                AsyncHttpClient client = new AsyncHttpClient();


                String address = "https://api.themoviedb.org/3/search/movie?api_key=" + Const.API_KEY +
                        "&language=en-US&query=" + edtSearch.getText().toString() + "&page=1&include_adult=false";


                client.get(address, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.i("Response", response.toString());
                        Gson gson = new Gson();
                        SearchResult result = gson.fromJson(response.toString(), SearchResult.class);
                        adapter.setResultItems(result.getResults());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                });
                break;
            }
        }
    }

    @Override
    public void itemClicked(int Id) {
        Intent intent = new Intent(MovieSearchActivity.this, MovieDetailsActivity.class);
        intent.putExtra(Const.TAG_ITEM_ID, Id);
        startActivity(intent);
    }
}
