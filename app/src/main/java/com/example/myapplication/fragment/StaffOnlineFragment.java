package com.example.myapplication.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProcessingAdapter;
import com.example.myapplication.adapter.StaffInforIsOnLineAdapter;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffOnlineFragment extends Fragment {
    //
    private RecyclerView rcStaff;
    private List<User> userList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_staff_is_online, container, false);
        rcStaff = rootView.findViewById(R.id.rcStaff);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(requireContext(), 1);
        rcStaff.setLayoutManager(linearLayoutManager);
        getListUsers();
        return rootView;
    }
    private void getListUsers() {
        APIService.apiService.getListUsersOnline()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        userList = response.body();
                        if (response.isSuccessful() && response.body() != null) {
                            userList = response.body();
                            StaffInforIsOnLineAdapter staffInforIsOnLineAdapter = new StaffInforIsOnLineAdapter(userList);
                            rcStaff.setAdapter(staffInforIsOnLineAdapter);
                        } else {
                            Log.e("StaffOnlineFragment", "Get list staff online failed ");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        Log.e("API Error", "Call API error: " + t.getMessage(), t);
                    }
                });
    }
}
