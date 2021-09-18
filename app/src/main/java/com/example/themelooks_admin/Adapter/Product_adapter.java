package com.example.themelooks_admin.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themelooks_admin.Model.Products.Products_response;
import com.example.themelooks_admin.R;

import java.util.List;

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.AppViewholder> {

    private List<Products_response> productsList;

    public Product_adapter(List<Products_response> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_product, parent, false);
        return new Product_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Products_response response = productsList.get(position);
        holder.productName.setText(response.getName());

        String imageData = response.getImage();

        if (!imageData.equals("xyz")) {
            Bitmap bm = StringToBitMap(imageData);
            holder.productImage.setImageBitmap(bm);
        } else {
            holder.productImage.setImageResource(R.drawable.google_icon);
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Product_adapter.OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView productName;
        ImageView productImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productNameID);
            productImage = itemView.findViewById(R.id.productImageID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
