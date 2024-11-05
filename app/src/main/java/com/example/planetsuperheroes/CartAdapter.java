package com.example.planetsuperheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetsuperheroes.models.OrderItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<OrderItem> orderItems;

    public CartAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.productNameTextView.setText(item.getProductName()); // Mostrar solo el nombre del producto
        holder.quantityTextView.setText("Cantidad: " + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void updateCartItems(List<OrderItem> newOrderItems) {
        orderItems = newOrderItems;
        notifyDataSetChanged();
    }

    // Método para obtener la lista de OrderItems
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView quantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView); // Asegúrate de que este ID coincida
            quantityTextView = itemView.findViewById(R.id.quantity);
        }
    }
}
