package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AddressAdapter;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.Address;
import com.example.myapplication.utils.ToastUtils;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    //
    private RecyclerView rcAddress;
    private List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_address);
        rcAddress = findViewById(R.id.rcAddress);
        ImageView backBtn = findViewById(R.id.backBtn);
        Button nextBtn = findViewById(R.id.nextBtn);
        Button addAddressBtn = findViewById(R.id.addAddressBtn);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        rcAddress.setLayoutManager(linearLayoutManager);
        callApiToGetAddress();
        backBtn.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(AddressActivity.this, CartActivity.class);
            startActivity(intent);
        });
        nextBtn.setOnClickListener(v -> {
            Address selectedAddress = null;
            for (Address address : addressList) {
                if (address.isSelected()) {
                    selectedAddress = address;
                    break;
                }
            }
            if (selectedAddress != null) {
                updateDataAddress(selectedAddress);
                finish();
                Intent intent = new Intent(AddressActivity.this, DiscountActivity.class);
                startActivity(intent);
            } else {
                if(addressList == null ) {
                    ToastUtils.showCustomToast(this, "Vui lòng thêm địa chỉ");
                } else {
                    ToastUtils.showCustomToast(this, "Vui lòng chọn địa chỉ giao hàng");
                }
            }
        });
        addAddressBtn.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
            startActivity(intent);
        });
    }
    private String getUsernameFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
    private void updateDataAddress(Address selectedAddress) {
        String username = getUsernameFromSharedPreferences();
        APIService.apiService.updateDataAddress(username, selectedAddress).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("Address Activity", "Call api to get update successfully");
                } else {
                    Log.e("Address Activity", "Call api to get update failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("API Error", "Call API error: " + t.getMessage(), t);
            }
        });
    }
    private void callApiToGetAddress() {
        String username = getUsernameFromSharedPreferences();
        APIService.apiService.getListAddress(username).enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressList = response.body();
                    AddressAdapter addressAdapter = new AddressAdapter(addressList, position -> {
                        // Xử lý sự kiện khi một hàng được chọn
                        handleAddressSelection(position);
                    });
                    rcAddress.setAdapter(addressAdapter);
                    Log.e("Address Activity", "Call api to get address successfully");
                } else {
                    Log.e("Address Activity", "Call api to get address failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable t) {
                Log.e("API Error", "Call API error: " + t.getMessage(), t);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void handleAddressSelection(int position) {
        // Cập nhật trạng thái của các hàng trong danh sách địa chỉ
        for (int i = 0; i < addressList.size(); i++) {
            addressList.get(i).setSelected(i == position);
        }
        // Cập nhật lại adapter sau khi có sự thay đổi
        Objects.requireNonNull(rcAddress.getAdapter()).notifyDataSetChanged();
    }

}