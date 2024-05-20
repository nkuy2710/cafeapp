package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.PaymentMethodAdapter;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.PaymentMethod;
import com.example.myapplication.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodsActivity extends AppCompatActivity implements PaymentMethodAdapter.OnItemClickListener{
    //
    private String username;
    private Dialog progressDialog;
    private RecyclerView recyclerView;
    private PaymentMethodAdapter adapter;
    private String selectedPaymentMethod;
    private List<PaymentMethod> paymentMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment_methods);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod(R.drawable.ic_cash, "Thanh toán tiền mặt", "Thanh toán khi nhận hàng"));
        paymentMethods.add(new PaymentMethod(R.drawable.ic_credit_card, "Credit or debit card", "Thẻ Visa hoặc Mastercard"));
        paymentMethods.add(new PaymentMethod(R.drawable.ic_bank_transfer, "Chuyển khoản ngân hàng", "Tự động xác nhận"));
        paymentMethods.add(new PaymentMethod(R.drawable.ic_zalopay, "ZaloPay", "Tự động xác nhận"));

        adapter = new PaymentMethodAdapter(paymentMethods, this);
        recyclerView.setAdapter(adapter);
        getUsernameFromSharedPreferences();

        Button confirmBtn = findViewById(R.id.cfmBtn);
        confirmBtn.setOnClickListener(v -> {
            if (selectedPaymentMethod != null) {
                setPaymentMethodInCart(selectedPaymentMethod);
                setProductInCartIsConfirmed();
                finish();
                Intent intent = new Intent(PaymentMethodsActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
            } else {
                ToastUtils.showCustomToast(this, "Vui lòng chọn phương thức thanh toán");
            }
        });
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(PaymentMethodsActivity.this, DiscountActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClick(String title) {
        selectedPaymentMethod = title;
    }
    private void getUsernameFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.dialog_loading);
            progressDialog.setCancelable(false);
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        }
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void setProductInCartIsConfirmed() {
        if (username.isEmpty()) {
            return;
        }
        showProgressDialog();
        APIService.apiService.setProductInCartIsConfirmed(username)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.e("PaymentMethodActivity", "Set is ordered:  " + "Successfully");
                            hideProgressDialog();
                        } else {
                            Log.e("PaymentMethodActivity", "Set is ordered:  " + "Failed");
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.e("API Error", "Call API error: " + t.getMessage(), t);
                        hideProgressDialog();
                    }
                });
    }
    private void setPaymentMethodInCart(String title) {
        if (username.isEmpty()) {
            return;
        }
        showProgressDialog();
        APIService.apiService.setPaymentMethodInCart(username, title)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.e("PaymentMethodActivity", "Save payment method:  " + "Successfully");
                            hideProgressDialog();
                        } else {
                            Log.e("PaymentMethodActivity", "Save payment method:  " + "Failed");
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.e("API Error", "Call API error: " + t.getMessage(), t);
                        hideProgressDialog();
                    }
                });
    }
}