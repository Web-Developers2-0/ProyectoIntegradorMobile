package com.example.planetsuperheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<OrderItem> cartItems;

    // Constructor
    public CartAdapter(Context context, List<OrderItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>(); // Evitar NullPointerException
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem orderItem = cartItems.get(position);
        holder.productName.setText("Producto ID: " + orderItem.getProduct());
        holder.quantity.setText("Cantidad: " + orderItem.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // Método para actualizar los elementos del carrito
    public void updateCartItems(List<OrderItem> newCartItems) {
        this.cartItems.clear(); // Limpiar la lista actual
        if (newCartItems != null) {
            this.cartItems.addAll(newCartItems); // Agregar nuevos elementos
        }
        notifyDataSetChanged(); // Notificar que los datos han cambiado
    }

    // Nuevo método para obtener la lista de OrderItem
    public List<OrderItem> getOrderItems() {
        return cartItems; // Devuelve la lista de OrderItem
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
