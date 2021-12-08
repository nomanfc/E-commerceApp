package com.ecommerce.app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecommerce.app.R;
import com.ecommerce.app.activity.Activity_ShowAll;
import com.ecommerce.app.adapters.PopularProductAdapter;
import com.ecommerce.app.adapters.CategoryAdapter;
import com.ecommerce.app.adapters.NewProductAdapter;
import com.ecommerce.app.adapters.SliderAdapter;
import com.ecommerce.app.models.CategoryModel;
import com.ecommerce.app.models.NewProductModel;
import com.ecommerce.app.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    int images[] = {R.drawable.one, R.drawable.two, R.drawable.three};

    RecyclerView catRecycle, newRecycle, popularRecycle;

    TextView catShowAll, newShowAll, popularShowAll;


    CategoryAdapter catAdap;
    List<CategoryModel> categoryModelList;

    NewProductAdapter newAdap;
    List<NewProductModel> newProductModelList;

    PopularProductAdapter popAdap;
    List<PopularProductModel> popProductModelList;

    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Home slider
        SliderView sv = root.findViewById(R.id.imageSlider);
        SliderAdapter sa = new SliderAdapter(images);

        sv.setSliderAdapter(sa);
        sv.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sv.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sv.setScrollTimeInSec(3);
        sv.startAutoCycle();

        //category
        catRecycle = root.findViewById(R.id.rec_category);

        //new product
        newRecycle = root.findViewById(R.id.new_product_rec);

        //All product
        popularRecycle = root.findViewById(R.id.popular_rec);

        //show all
        catShowAll = root.findViewById(R.id.category_see_all);
        newShowAll = root.findViewById(R.id.newProducts_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);


        newShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Activity_ShowAll.class));
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Activity_ShowAll.class));
            }
        });

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Activity_ShowAll.class));
            }
        });





        db = FirebaseFirestore.getInstance();

        //category
        catRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        catAdap = new CategoryAdapter(getContext(),categoryModelList);
        catRecycle.setAdapter(catAdap);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel cateModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(cateModel);
                                catAdap.notifyDataSetChanged();
                            }
                        } else {

                        }
                    }
                });

        //new product
        newRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductModelList = new ArrayList<>();
        newAdap = new NewProductAdapter(getContext(),newProductModelList);
        newRecycle.setAdapter(newAdap);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductModel newModel = document.toObject(NewProductModel.class);
                                newProductModelList.add(newModel);
                                newAdap.notifyDataSetChanged();
                            }
                        } else {

                        }
                    }
                });

        //popular product
        popularRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popProductModelList = new ArrayList<>();
        popAdap = new PopularProductAdapter(getContext(),popProductModelList);
        popularRecycle.setAdapter(popAdap);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductModel popModel = document.toObject(PopularProductModel.class);
                                popProductModelList.add(popModel);
                                popAdap.notifyDataSetChanged();
                            }
                        } else {

                        }
                    }
                });

        return root;
    }
}