package com.example.myapplication.raihanapplicationmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    MovieFeedback Mofe;


    OkHttpClient client = new OkHttpClient();


    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView firstRV=findViewById(R.id.list);
        firstRV.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

        firstRV.setAdapter(new RaihanAdapter());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String feedback;
        try {
            feedback=run("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&page=1&api_key=3fa9058382669f72dcb18fb405b7a831&language=en-US");
            Mofe= new Gson().fromJson(feedback,MovieFeedback.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    class RaihanAdapter extends RecyclerView.Adapter<ForView>{

        @NonNull
        @Override
        public ForView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.list_of_movies,parent,false);
            return new ForView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ForView holder, int position) {
            holder.ratings.setText(""+Mofe.getResults().get(position).getVoteAverage());
            holder.Title.setText(Mofe.getResults().get(position).getTitle());
            Glide
                    .with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/"+Mofe.getResults().get(position).getPosterPath())
                    .centerCrop()
                    .into(holder.poster);
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent1=new Intent(MainActivity.this,DetailsMovies.class);
                    intent1.putExtra("feedback",Mofe.getResults().get(position));
                    startActivity(intent1);
                }
            });

        }

        @Override
        public int getItemCount() {
            int space=Mofe.getResults().size();
            return space;
        }
    }

    class ForView extends RecyclerView.ViewHolder{

        TextView ratings,Title;
        ImageView poster;

        public ForView(@NonNull View itemView) {
            super(itemView);
            ratings=itemView.findViewById(R.id.rating_of_the_movie);
            Title=itemView.findViewById(R.id.title_of_the_movie);
            poster=itemView.findViewById(R.id.photo_of_the_movie);
        }
    }
}