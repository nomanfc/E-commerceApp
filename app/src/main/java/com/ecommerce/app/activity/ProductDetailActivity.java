package com.ecommerce.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecommerce.app.R;
import com.ecommerce.app.models.NewProductModel;
import com.ecommerce.app.models.PopularProductModel;
import com.ecommerce.app.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price, quantity;
    Button buyNow, addItem, removeItem, addToCart;
    Toolbar toolbar;


    int totalQuantity = 1;
    double totalPrice = 0;

    //new product
    NewProductModel newProductModel = null;
    //popular product
    PopularProductModel popularProductModel = null;

    ShowAllModel showAllModel = null;

    private FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar =  findViewById(R.id.detail_menu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final Object obj = getIntent().getSerializableExtra("detailed");


        if(obj instanceof NewProductModel){
            newProductModel = (NewProductModel) obj;
        }

        if(obj instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) obj;
        }

        if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.productView);
        name = findViewById(R.id.nameView);
        rating = findViewById(R.id.ratingView);
        price = findViewById(R.id.priceView);
        description = findViewById(R.id.detailView);
        buyNow = findViewById(R.id.BuyNow);
        addItem = findViewById(R.id.Add);
        removeItem = findViewById(R.id.Remove);
        addToCart = findViewById(R.id.AddCart);
        quantity = findViewById(R.id.quantity);



        //new products detail
        if(newProductModel != null){
            Glide.with(getApplicationContext()).load(newProductModel.getImg_url()).into(detailedImg);
            name.setText(newProductModel.getName());
            rating.setText(newProductModel.getRating());
            description.setText(newProductModel.getDescription());
            price.setText(newProductModel.getPrice());


            String itemPrice = newProductModel.getPrice();
            double pricePerItem = Double.parseDouble(itemPrice);
            totalPrice = pricePerItem * totalQuantity;

        }

        //popular product detail
        if(popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailedImg);
            name.setText(popularProductModel.getName());
            rating.setText(popularProductModel.getRating());
            description.setText(popularProductModel.getDescription());
            price.setText(popularProductModel.getPrice());

            String itemPrice = popularProductModel.getPrice();
            double pricePerItem = Double.parseDouble(itemPrice);
            totalPrice = pricePerItem * totalQuantity;
        }

        //all product detail
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(showAllModel.getPrice());

            String itemPrice = showAllModel.getPrice();
            double pricePerItem = Double.parseDouble(itemPrice);
            totalPrice = pricePerItem * totalQuantity;
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10){
                    totalQuantity = totalQuantity + 1;
                    quantity.setText(String.valueOf(totalQuantity));
                }

                if(newProductModel != null){
                    String itemPrice = newProductModel.getPrice();
                    double pricePerItem = Double.parseDouble(itemPrice);
                    totalPrice = pricePerItem * totalQuantity;
                }
                if(popularProductModel != null){
                    String itemPrice = popularProductModel.getPrice();
                    double pricePerItem = Double.parseDouble(itemPrice);
                    totalPrice = pricePerItem * totalQuantity;
                }
                if(showAllModel != null){
                    String itemPrice = showAllModel.getPrice();
                    double pricePerItem = Double.parseDouble(itemPrice);
                    totalPrice = pricePerItem * totalQuantity;
                }

            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity = totalQuantity - 1;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });



        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCart();
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetailActivity.this, Activity_Address.class));
            }
        });
    }

    public void AddCart(){
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("h:mm aa");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        String totalCost = String.valueOf(totalPrice);
        cartMap.put("ProductName", name.getText().toString());
        cartMap.put("ProductPrice", price.getText().toString());
        cartMap.put("CurrentTime",saveCurrentTime);
        cartMap.put("CurrentDate", saveCurrentDate);
        cartMap.put("TotalQuantity", totalQuantity);
        cartMap.put("TotalPrice", totalCost);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(ProductDetailActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}