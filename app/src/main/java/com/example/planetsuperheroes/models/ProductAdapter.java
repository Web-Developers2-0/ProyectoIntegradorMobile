package com.example.planetsuperheroes.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.planetsuperheroes.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList != null ? productList : new ArrayList<>();
        this.context = context;
        Log.d("ProductAdapter", "Número de productos: " + this.productList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());

        double calification = product.getCalification();
        Log.d("ProductAdapter", "Calificación: " + calification);

        String imageUrl = product.getImage();
        Log.d("ProductAdapter", "Image URL: " + imageUrl);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.productImage);
        } else {
            Log.e("ProductAdapter", "Image URL is null or empty for product: " + product.getName());
        }

        holder.productRating.setText(String.valueOf(calification));
        setStars(holder, calification);

        holder.itemView.setOnClickListener(v -> {
            Log.d("ProductAdapter", "Item clickeado: " + product.getName() + ", ID: " + product.getId());
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImage;
        TextView productRating;
        ImageView star1, star2, star3, star4, star5;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productRating = itemView.findViewById(R.id.productRating);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
        }
    }

    private void setStars(ViewHolder holder, double rating) {
        int fullStars = (int) rating;
        boolean hasHalfStar = (rating % 1) >= 0.5;

        Log.d("ProductAdapter", "Full Stars: " + fullStars + ", Half Star: " + hasHalfStar);

        clearStars(holder);

        for (int i = 1; i <= fullStars; i++) {
            getStarImageView(holder, i).setImageResource(R.drawable.star);
        }

        if (hasHalfStar) {
            getStarImageView(holder, fullStars + 1).setImageResource(R.drawable.rating);
        }

        for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) {
            getStarImageView(holder, i + 1).setImageResource(R.drawable.white_star);
        }
    }

    private void clearStars(ViewHolder holder) {
        holder.star1.setImageResource(R.drawable.white_star);
        holder.star2.setImageResource(R.drawable.white_star);
        holder.star3.setImageResource(R.drawable.white_star);
        holder.star4.setImageResource(R.drawable.white_star);
        holder.star5.setImageResource(R.drawable.white_star);
    }

    private ImageView getStarImageView(ViewHolder holder, int index) {
        switch (index) {
            case 1: return holder.star1;
            case 2: return holder.star2;
            case 3: return holder.star3;
            case 4: return holder.star4;
            case 5: return holder.star5;
            default: return null;
        }
    }
}


