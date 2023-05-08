package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fooddeliveryapp.databinding.ActivityOrderPaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class OrderPaymentActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityOrderPaymentBinding binding;
    static int counter = 1;
    private String price="",name="",encoded="";
    static int new_price = 0;
    private DatabaseReference mdatabase;
    private FirebaseAuth auth;
    static int product_price = 0;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        name = intent.getStringExtra("product");
        price = intent.getStringExtra("price");
        product_price  = Integer.parseInt(price);

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        encoded = prefs.getString("image", "");

        byte[] byteArray = Base64.decode(encoded, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        binding.productimg.setImageBitmap(bitmap);

        binding.productName.setText(name);

        binding.tvprice.setText(price+" \u20B9");

        mdatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        binding.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds 1 to the counter
                try{
                    counter = counter + 1;
                    binding.tvitemtimes.setText(String.valueOf(counter));
                    price = String.valueOf(counter*product_price);
                    binding.tvprice.setText(price +" \u20B9");
                }
                catch (Exception e){
                    Toast.makeText(OrderPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Subtract 1 from counter
                try{
                    if(counter>1){
                        counter = counter - 1;
                        new_price= Integer.parseInt(price)-product_price;
                        price = String.valueOf(new_price);
                        binding.tvprice.setText(price+" \u20B9");
                        binding.tvitemtimes.setText(String.valueOf(counter));
                    }
                }catch (Exception e){
                    Toast.makeText(OrderPaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.btpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int payment = Integer.parseInt(price)*100;
                makePayment(String.valueOf(payment));
            }

        });
    }
    private void makePayment(String price) {


        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_S9nfQQTurXm5WQ");
        checkout.setImage(R.drawable.app_logo);


        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
            String username = preferences.getString("username","");
            options.put("name",username );
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("image", R.drawable.app_logo);
            options.put("amount", price);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9532875308");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("sat", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        HashMap<String,String> order = new HashMap<>();
        order.put("item_name",name);
        order.put("item_price",price);
        order.put("item_img",encoded);
        order.put("item_quantity",String.valueOf(counter));
        order.put("payment","successful");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        order.put("date",date);

        String id = mdatabase.push().getKey();
        mdatabase.child("Order").child(auth.getUid()).child(id).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(OrderPaymentActivity.this, "Payment SuccessFul"+date, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        HashMap<String,String> order = new HashMap<>();
        order.put("item_name",name);
        order.put("item_price",price);
        order.put("item_img",encoded);
        order.put("item_quantity",String.valueOf(counter));
        order.put("payment","failed");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        order.put("date",date);

        String id = mdatabase.push().getKey();
        mdatabase.child("Order").child(auth.getUid()).child(id).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(OrderPaymentActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}