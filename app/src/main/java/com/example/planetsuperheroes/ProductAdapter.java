package com.example.planetsuperheroes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText("$" + product.getPrice());

        int imageResource = context.getResources().getIdentifier(product.getImage(), "drawable", context.getPackageName());
        if (imageResource != 0) {
            Glide.with(context)
                    .load(imageResource)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageDrawable(null); // Imagen por defecto
            holder.productImage.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice;
        ImageView productImage;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
