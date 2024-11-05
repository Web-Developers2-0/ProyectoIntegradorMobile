package com.example.planetsuperheroes.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class OrderItem implements Parcelable {
    @SerializedName("id_product")
    private int product; // ID del producto
    private String productName; // Nombre del producto
    @SerializedName("quantity")
    private int quantity; // Cantidad del producto

    // Constructor
    public OrderItem(int product, String productName, int quantity) {
        this.product = product;
        this.productName = productName;
        this.quantity = quantity;
    }

    // Getters
    public int getProduct() {
        return product;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    // Métodos de Parcelable
    protected OrderItem(Parcel in) {
        product = in.readInt();
        productName = in.readString();
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(product);
        dest.writeString(productName);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}

