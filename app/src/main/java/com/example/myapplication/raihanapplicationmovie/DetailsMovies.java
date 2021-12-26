package com.example.myapplication.raihanapplicationmovie;

import static java.lang.Math.round;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsMovies extends AppCompatActivity {
    Result r;
    ForCast r2;

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
        setContentView(R.layout.activity_details_movies);
        TextView title=findViewById(R.id.title_raw);
        TextView rating=findViewById(R.id.ratings_second);
        TextView description=findViewById(R.id.description_of_movie);
        TextView rel=findViewById(R.id.release_day);
        ImageView photoBack=findViewById(R.id.background_photo);
        ImageView photoFront=findViewById(R.id.actual_photo);
        RatingBar rBar=findViewById(R.id.RaihanBar);


        r=(Result) getIntent().getSerializableExtra("feedback");
        int a=(((int)r.getVoteAverage())/2);

        title.setText(r.getTitle());
        description.setText(r.getOverview());
        rel.setText(r.getReleaseDate());
        rating.setText(""+r.getVoteAverage());
        ImageButton button=findViewById(R.id.back_key);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(DetailsMovies.this, MainActivity.class);
                startActivity(in);
            }
        });
        Glide
                .with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500/"+r.getPosterPath())
                .centerCrop()
                .into(photoFront);
        Glide
                .with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500/"+r.getBackdropPath())
                .centerCrop()
                .into(photoBack);

        rBar.setRating(a);

        RecyclerView sRV=findViewById(R.id.cast_of_movie);
        sRV.setLayoutManager(new LinearLayoutManager(DetailsMovies.this,LinearLayoutManager.HORIZONTAL,false));
        sRV.setAdapter(new DetailAdapter());

        String feedback2;
        try {
            feedback2=run("https://api.themoviedb.org/3/movie/"+r.getId()+"/credits?api_key=3fa9058382669f72dcb18fb405b7a831");
            r2=new Gson().fromJson(feedback2,ForCast.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    class DetailAdapter extends RecyclerView.Adapter<DetailView>{

        @NonNull
        @Override
        public DetailView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(DetailsMovies.this).inflate(R.layout.list_of_movies_sub,parent,false);
            return new DetailView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailView holder, int position) {
        holder.nameCast.setText(r2.getCast().get(position).getName());
            Glide
                    .with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/"+r2.getCast().get(position).getProfilePath())
                    .centerCrop()
                    .into(holder.castPicture);

        }

        @Override
        public int getItemCount() {
            int n=r2.getCast().size();
            return n;
        }
    }
    class DetailView extends RecyclerView.ViewHolder{
        ImageView castPicture;
        TextView nameCast;
        public DetailView(@NonNull View itemView) {
            super(itemView);
            castPicture=itemView.findViewById(R.id.cast_person);
            nameCast=itemView.findViewById(R.id.person_name);
        }
    }
}