package com.ecommerce.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.app.R;
import com.ecommerce.app.activity.Activity_Cart;
import com.ecommerce.app.activity.Activity_ShowAll;
import com.ecommerce.app.models.CartModel;
import com.ecommerce.app.models.CategoryModel;
import com.ecommerce.app.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context ;
    private List<CartModel> list;
    double totalAmount = 0;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
        db = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_rec_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductPrice());
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.totalQuantity.setText(String.valueOf(list.get(position).getTotalQuantity()));
        holder.totalPrice.setText(list.get(position).getTotalPrice());

        double amount = Double.parseDouble(list.get(position).getTotalPrice());
        totalAmount = totalAmount + amount;
        Intent i = new Intent("MyTotalAmount");
        i.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(i);


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();

                                    Intent i = new Intent(context, Activity_Cart.class);
                                    context.startActivity(i);

                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, date, time, totalQuantity, totalPrice;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            btn = itemView.findViewById(R.id.delete_cart_item);
        }
    }
}
