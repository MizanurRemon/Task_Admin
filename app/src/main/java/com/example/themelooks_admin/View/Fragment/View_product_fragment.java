package com.example.themelooks_admin.View.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themelooks_admin.Adapter.Variant_adapter;
import com.example.themelooks_admin.Model.APIUtilize;
import com.example.themelooks_admin.Model.Products.Products_API;
import com.example.themelooks_admin.Model.Products.Products_response;
import com.example.themelooks_admin.Model.Products.Products_variant_response;
import com.example.themelooks_admin.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_product_fragment extends Fragment {

    ImageView backButton;
    String productID, color, size, price, description, image;
    TextView productNameText, descriptionText;
    ImageView productImage;
    RecyclerView variantView;
    Products_API productsApi;
    List<Products_variant_response> variantList;
    Variant_adapter adapter;

    public View_product_fragment(String productID) {
        this.productID = productID;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();

        product_variant_list();
    }

    private void product_variant_list() {

        //product variant list call
        productsApi.getProductVariant(productID).enqueue(new Callback<List<Products_variant_response>>() {
            @Override
            public void onResponse(Call<List<Products_variant_response>> call, Response<List<Products_variant_response>> response) {
                //Toast.makeText(getActivity(), String.valueOf(response.body().size()), Toast.LENGTH_SHORT).show();
                variantList = new ArrayList<>();
                variantList = response.body();
                adapter = new Variant_adapter(variantList);
                variantView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Products_variant_response>> call, Throwable t) {

            }
        });
    }

    private void main() {

       // product details API call
        productsApi.getProductDetails(productID).enqueue(new Callback<Products_response>() {
            @Override
            public void onResponse(Call<Products_response> call, Response<Products_response> response) {

                productNameText.setText(response.body().getName());
                descriptionText.setText(response.body().getDescription());
                image = response.body().getImage();
                Bitmap bm = StringToBitMap(image);
                productImage.setImageBitmap(bm);

            }

            @Override
            public void onFailure(Call<Products_response> call, Throwable t) {

            }
        });

    }


    //string to image convertion
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

        productsApi = APIUtilize.productsApi();

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        productNameText = view.findViewById(R.id.productNameID);

        descriptionText = view.findViewById(R.id.descriptionTextID);
        productImage = view.findViewById(R.id.productImageID);

        variantView = view.findViewById(R.id.variantViewID);
        variantView.setHasFixedSize(true);
        variantView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}