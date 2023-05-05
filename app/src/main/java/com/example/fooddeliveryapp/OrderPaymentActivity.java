package com.example.fooddeliveryapp;

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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class OrderPaymentActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityOrderPaymentBinding binding;
    static int counter = 1;
    String price="";
    static int new_price = 0;
    static int product_price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String name = intent.getStringExtra("product");
        price = intent.getStringExtra("price");
        product_price  = Integer.parseInt(price);

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String encoded = prefs.getString("image", "");

        byte[] byteArray = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        binding.productimg.setImageBitmap(bitmap);

        binding.productName.setText(name);

        binding.tvprice.setText(price+" \u20B9");

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



        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
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
        Toast.makeText(this, "Payment SuccessFul", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment UnSuccessFul", Toast.LENGTH_SHORT).show();

    }
}