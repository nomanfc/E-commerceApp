package com.ecommerce.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.ecommerce.app.R;
import com.ecommerce.app.activity.ProductDetailActivity;
import com.ecommerce.app.models.PopularProductModel;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {

    private Context context ;
    private List<PopularProductModel> list;

    public PopularProductAdapter(Context context,List<PopularProductModel> list ) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imgView);
        holder.nameView.setText(list.get(position).getName());
        holder.priceView.setText(list.get(position).getPrice());
        holder.ratingView.setText(list.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("detailed", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        TextView priceView, ratingView, nameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.all_img);
            nameView = itemView.findViewById(R.id.all_product_name);
            priceView = itemView.findViewById(R.id.all_price);
            ratingView = itemView.findViewById(R.id.rating_view);
        }
    }
}
