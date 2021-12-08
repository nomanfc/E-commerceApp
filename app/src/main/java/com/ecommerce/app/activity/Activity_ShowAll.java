package com.ecommerce.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ecommerce.app.R;
import com.ecommerce.app.adapters.ShowAllAdapter;
import com.ecommerce.app.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Activity_ShowAll extends AppCompatActivity {

    RecyclerView recView;
    FirebaseFirestore db;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel>list;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar_showAll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        String type = getIntent().getStringExtra("type");

        recView = findViewById(R.id.show_all_rec);
        recView.setLayoutManager(new GridLayoutManager(this, 2));
        list = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, list);
        recView.setAdapter(showAllAdapter);



        if(type == null || type.isEmpty()){
            db.collection("All")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("phones")){
            db.collection("All").whereEqualTo("type", "phones")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("shoes")){
            db.collection("All").whereEqualTo("type", "shoes")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("cloths")){
            db.collection("All").whereEqualTo("type", "cloths")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("headphones")){
            db.collection("All").whereEqualTo("type", "headphones")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("computers")){
            db.collection("All").whereEqualTo("type", "computers")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    list.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        }
                    });
        }
    }

}