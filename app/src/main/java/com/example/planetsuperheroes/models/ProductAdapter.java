package com.example.planetsuperheroes.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.planetsuperheroes.CartManager;
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

        // Inicializar la cantidad
        holder.quantity = 0; // Inicializamos la cantidad en 0
        holder.txtQuantity.setText(String.valueOf(holder.quantity)); // Mostrar cantidad inicial

        // Incrementar cantidad
        holder.btnAdd.setOnClickListener(v -> {
            holder.quantity++;
            holder.txtQuantity.setText(String.valueOf(holder.quantity)); // Actualizar el TextView
        });

        // Decrementar cantidad
        holder.btnRemove.setOnClickListener(v -> {
            if (holder.quantity > 0) {
                holder.quantity--;
                holder.txtQuantity.setText(String.valueOf(holder.quantity)); // Actualizar el TextView
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Log.d("ProductAdapter", "Item clickeado: " + product.getName() + ", ID: " + product.getId());
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("productId", product.getId());
            context.startActivity(intent);
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addProductToCart(product, holder.quantity); // Agrega el producto al carrito con la cantidad
            Toast.makeText(context, product.getName() + " agregado al carrito. Cantidad: " + holder.quantity, Toast.LENGTH_SHORT).show();
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
        TextView txtQuantity; // Para mostrar la cantidad
        View btnAdd; // Botón para incrementar
        View btnRemove; // Botón para decrementar
        View btnAddToCart; // Botón para agregar al carrito
        int quantity; // Para almacenar la cantidad actual

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productRating = itemView.findViewById(R.id.productRating);
            txtQuantity = itemView.findViewById(R.id.txtQuantity); // Asegúrate de que el TextView esté en tu layout
            btnAdd = itemView.findViewById(R.id.btnAdd); // Botón para incrementar
            btnRemove = itemView.findViewById(R.id.btnRemove); // Botón para decrementar
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
