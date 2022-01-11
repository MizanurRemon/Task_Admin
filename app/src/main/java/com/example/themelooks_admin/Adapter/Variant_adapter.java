package com.example.themelooks_admin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themelooks_admin.Model.Products.Products_variant_response;
import com.example.themelooks_admin.R;

import java.util.List;

public class Variant_adapter extends RecyclerView.Adapter<Variant_adapter.AppViewHolder> {
    List<Products_variant_response> variantList;

    public Variant_adapter(List<Products_variant_response> variantList) {
        this.variantList = variantList;
    }

    @NonNull
    @Override
    public Variant_adapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.variant_card, parent, false);
        return new Variant_adapter.AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Variant_adapter.AppViewHolder holder, int position) {
        Products_variant_response response = variantList.get(position);
        holder.sizeText.setText(response.getSize());
        holder.colorText.setText(response.getColor());
        holder.amountText.setText(response.getAmount());
        holder.priceRangeText.setText(response.getPriceRange());

    }

    @Override
    public int getItemCount() {
        return variantList.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        TextView sizeText, colorText, amountText, priceRangeText;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeText = itemView.findViewById(R.id.sizeTextID);
            colorText = itemView.findViewById(R.id.colorTextID);
            amountText = itemView.findViewById(R.id.amountTextID);
            priceRangeText = itemView.findViewById(R.id.priceRangeTextID);
        }
    }
}
