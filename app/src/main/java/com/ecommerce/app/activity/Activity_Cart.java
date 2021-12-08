package com.ecommerce.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.app.R;
import com.ecommerce.app.adapters.CartAdapter;
import com.ecommerce.app.models.CartModel;
import com.ecommerce.app.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Activity_Cart extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<CartModel> list;
    CartAdapter cartAdapter;
    double totalBill;

    Button btn;

    TextView amount;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn = findViewById(R.id.BuyNowFromCart);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Cart.this, Activity_Address.class));
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_Cart.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        amount = findViewById(R.id.amount);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessage, new IntentFilter( "MyTotalAmount"));


        recyclerView = findViewById(R.id.cart_items_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        cartAdapter = new CartAdapter(this, list);
        recyclerView.setAdapter(cartAdapter);


        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String documentId = document.getId();

                        CartModel cartModel = document.toObject(CartModel.class);
                        cartModel.setDocumentId(documentId);
                        list.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    public BroadcastReceiver mMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalBill = intent.getDoubleExtra("totalAmount", 0);
            amount.setText(String.valueOf(totalBill)+" Taka");
         }
    };
}