package com.example.planetsuperheroes.models;

import android.content.Context;
import android.content.Intent;
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
        holder.productRating.setText(String.valueOf(product.getCalification()));

        // Cargar imagen usando Glide
        Glide.with(context)
                .load(product.getImage())
                .into(holder.productImage);

        holder.quantity = 0; // Inicializar la cantidad
        holder.txtQuantity.setText("0");
        holder.btnAddToCart.setEnabled(false); // Deshabilitar botón al inicio

        holder.btnAdd.setOnClickListener(v -> {
            holder.quantity++;
            holder.txtQuantity.setText(String.valueOf(holder.quantity));
            holder.btnAddToCart.setEnabled(holder.quantity > 0); // Habilitar si hay cantidad
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (holder.quantity > 0) {
                holder.quantity--;
                holder.txtQuantity.setText(String.valueOf(holder.quantity));
                holder.btnAddToCart.setEnabled(holder.quantity > 0); // Deshabilitar si es 0
            }
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            try {
                if (holder.btnAddToCart.isEnabled()) {
                    CartManager.getInstance().addProductToCart(product, holder.quantity);
                    Toast.makeText(context, product.getName() + " agregado al carrito. Cantidad: " + holder.quantity, Toast.LENGTH_SHORT).show();
                    holder.quantity = 0; // Resetea la cantidad
                    holder.txtQuantity.setText("0");
                    holder.btnAddToCart.setEnabled(false);
                } else {
                    Toast.makeText(context, "Selecciona una cantidad mayor a 0 para agregar al carrito", Toast.LENGTH_SHORT).show();
                }
            } catch (IllegalArgumentException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Manejo del clic en el elemento del producto
        holder.itemView.setOnClickListener(v -> {
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
        TextView productName, productRating, txtQuantity;
        ImageView productImage;
        View btnAdd, btnRemove, btnAddToCart;
        int quantity; // Variable para la cantidad

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productRating = itemView.findViewById(R.id.productRating);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
