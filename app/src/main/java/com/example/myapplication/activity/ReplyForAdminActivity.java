package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ReplyAdapter;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.Reply;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyForAdminActivity extends AppCompatActivity {

    private List<Reply> replyList;
    private RecyclerView rcReply;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reply_for_admin);

        backBtn = findViewById(R.id.backBtn);
        rcReply = findViewById(R.id.rcReply);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        rcReply.setLayoutManager(linearLayoutManager);
        callApiGetListReply();
        backBtn.setOnClickListener(v -> finish());
    }

    private void callApiGetListReply() {
        APIService.apiService.getContentReply().enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reply>> call, @NonNull Response<List<Reply>> response) {
                if (response.isSuccessful()) {
                    replyList = response.body();
                    ReplyAdapter replyAdapter = new ReplyAdapter(replyList);
                    rcReply.setAdapter(replyAdapter);
                } else {
                    Log.e("ReplyForAdminActivity", "Get list contetn failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reply>> call, @NonNull Throwable t) {
                Log.e("API Error", "Call API error: " + t.getMessage(), t);

            }
        });
    }
}