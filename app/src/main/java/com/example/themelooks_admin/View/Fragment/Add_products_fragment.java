package com.example.themelooks_admin.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themelooks_admin.Model.APIUtilize;
import com.example.themelooks_admin.Model.AddProducts.Add_product_response;
import com.example.themelooks_admin.Model.AddProducts.Add_products_API;
import com.example.themelooks_admin.Model.AddProducts.Price_response;
import com.example.themelooks_admin.Model.AddProducts.Product_variant;
import com.example.themelooks_admin.R;
import com.example.themelooks_admin.ViewModel.AddProductsViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_products_fragment extends Fragment {

    ImageView backButton;
    String userID;
    AddProductsViewModel addProductsViewModel;
    TextView addProductButton, descriptionText;
    EditText productName;

    ImageView pickImageButton;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    String imgdata;
    int check = 0, final_check = 0;
    Dialog loader;
    Add_products_API addProductsApi;
    ImageView addVariantButton;
    EditText sizeEditText, colorEditText, amountEditText, priceRangeEditText;

    List<Product_variant> variantList;
    int variant = 0;
    Product_variant productVariant;
    int count = 0;


    public Add_products_fragment(String userID) {
        this.userID = userID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void price_func(String color, String size) {

        addProductsViewModel.getPrice(color, size).observe(getViewLifecycleOwner(), new Observer<Price_response>() {
            @Override
            public void onChanged(Price_response price_response) {
                String price = price_response.getPrice();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, false);
                    //selectedImage.setImageBitmap(bitmap);
                    pickImageButton.setImageURI(filepath);
                    check = 1;
                    //uriStrng = uri.toString();
                    //Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_products_fragment, container, false);

        addVariantButton = view.findViewById(R.id.addVariantButtonID);
        sizeEditText = view.findViewById(R.id.sizeEditTextID);
        colorEditText = view.findViewById(R.id.colorEditTextID);
        amountEditText = view.findViewById(R.id.amountEditTextID);
        priceRangeEditText = view.findViewById(R.id.priceRangeEditTextID);


        addProductsViewModel = new ViewModelProvider(this).get(AddProductsViewModel.class);
        addProductsApi = APIUtilize.addProductsApi();

        loader = new Dialog(getActivity());
        loader.setContentView(R.layout.loader_alert);
        loader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader.setCancelable(false);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        descriptionText = (TextView) view.findViewById(R.id.descriptionTextID);
        addProductButton = (TextView) view.findViewById(R.id.addProductButtonID);

        pickImageButton = (ImageView) view.findViewById(R.id.pickImageID);

        productName = (EditText) view.findViewById(R.id.productNameID);


        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = productName.getText().toString().trim();
                String description = descriptionText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getActivity(), "Empty product name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(description)) {
                        Toast.makeText(getActivity(), "Empty description", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    loader.show();
                    addProductsViewModel.getProduct(name, description, imgToString(bitmap)).observe(getViewLifecycleOwner(), new Observer<Add_product_response>() {
                        @Override
                        public void onChanged(Add_product_response add_product_response) {
                            String message = add_product_response.getMessage();


                            if (!message.equals("invalid")) {
                                //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                if (variant == 1) {
                                    for (int i = 0; i < variantList.size(); i++) {

                                        add_variant_func(message, variantList.get(i).getColor(), variantList.get(i).getSize(), variantList.get(i).getAmount(), variantList.get(i).getPrice_range());
                                    }
                                } else {
                                    String size = sizeEditText.getText().toString().toLowerCase().trim();
                                    String color = colorEditText.getText().toString().toLowerCase().trim();
                                    String amount = amountEditText.getText().toString().trim();
                                    String price_range = priceRangeEditText.getText().toString().trim();

                                    if (TextUtils.isEmpty(size) || TextUtils.isEmpty(color) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(price_range)) {
                                        Toast.makeText(getActivity(), "Empty filed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        productVariant.setSize(size);
                                        productVariant.setColor(color);
                                        productVariant.setAmount(amount);
                                        productVariant.setPrice_range(price_range);
                                        variantList.add(productVariant);

                                        for (int i = 0; i < variantList.size(); i++) {
                                            add_variant_func(message, variantList.get(i).getColor(), variantList.get(i).getSize(), variantList.get(i).getAmount(), variantList.get(i).getPrice_range());
                                        }
                                    }
                                }


                            } else {
                                loader.dismiss();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, IMAGE_REQUEST_CODE);
                imageselect();
            }
        });

        variantList = new ArrayList<>();
        productVariant = new Product_variant();

        addVariantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variant = 1;
                String size = sizeEditText.getText().toString().toLowerCase().trim();
                String color = colorEditText.getText().toString().toLowerCase().trim();
                String amount = amountEditText.getText().toString().trim();
                String price_range = priceRangeEditText.getText().toString().trim();

                if (TextUtils.isEmpty(size) || TextUtils.isEmpty(color) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(price_range)) {
                    Toast.makeText(getActivity(), "Empty filed", Toast.LENGTH_SHORT).show();
                } else {
                    productVariant.setSize(size);
                    productVariant.setColor(color);
                    productVariant.setAmount(amount);
                    productVariant.setPrice_range(price_range);
                    variantList.add(productVariant);

                    sizeEditText.getText().clear();
                    colorEditText.getText().clear();
                    amountEditText.getText().clear();
                    priceRangeEditText.getText().clear();

                    //Toast.makeText(getActivity(), String.valueOf(variantList.size()), Toast.LENGTH_SHORT).show();

                    for (int i = 0; i<variantList.size();i++){
                        Log.d("variantListXX", String.valueOf(variantList.get(i).getSize()));
                    }

                }

            }
        });

        return view;
    }

    private void add_variant_func(String message, String color, String size, String amount, String price_range) {
        addProductsApi.addFinalResponse(message, color, size, amount, price_range).enqueue(new Callback<Add_product_response>() {
            @Override
            public void onResponse(Call<Add_product_response> call, Response<Add_product_response> response) {
                String message = response.body().getMessage();

                if (message.equals("added")) {
                    count = count + 1;
                    if (count == variantList.size()) {
                        loader.dismiss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    loader.dismiss();
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add_product_response> call, Throwable t) {
                Log.d("errorxx", t.getMessage());
            }
        });

    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        //long lengthbmp = imgbytes.length;

        //Toast.makeText(getApplicationContext(), String.valueOf(lengthbmp/1024), Toast.LENGTH_SHORT).show();

        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    public void imageselect() {
        final CharSequence[] items = {"Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(new Intent(Intent.ACTION_GET_CONTENT));
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "select image"), IMAGE_REQUEST_CODE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


}