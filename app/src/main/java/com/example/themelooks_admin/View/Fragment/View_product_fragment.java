package com.example.themelooks_admin.View.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themelooks_admin.R;

public class View_product_fragment extends Fragment {

    ImageView backButton;
    String name, color, size, price, description, image;
    TextView productNameText, colorText, sizeText, priceText, descriptionText;
    ImageView productImage;

    public View_product_fragment(String name, String color, String size, String price, String description, String image) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();
    }

    private void main() {

        productNameText.setText(name);
        colorText.setText(color);
        sizeText.setText(size);
        priceText.setText(price);
        descriptionText.setText(description);

        if(!image.equals("xyz")){
            Bitmap bm = StringToBitMap(image);
            productImage.setImageBitmap(bm);
        } else {
            productImage.setImageResource(R.drawable.google_icon);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_product_fragment, container, false);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).replace(R.id.frame_container, new Home_fragment()).commit();
            }
        });

        productNameText = view.findViewById(R.id.productNameID);
        colorText = view.findViewById(R.id.colorTextID);
        sizeText = view.findViewById(R.id.sizeTextID);
        priceText = view.findViewById(R.id.priceTextID);
        descriptionText = view.findViewById(R.id.descriptionTextID);
        productImage = view.findViewById(R.id.productImageID);

        return view;
    }
}