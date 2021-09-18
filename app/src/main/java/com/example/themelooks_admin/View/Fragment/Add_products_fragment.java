package com.example.themelooks_admin.View.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Base64;
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

import com.example.themelooks_admin.Model.AddProducts.Add_product_response;
import com.example.themelooks_admin.Model.AddProducts.Price_response;
import com.example.themelooks_admin.R;
import com.example.themelooks_admin.ViewModel.AddProductsViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_products_fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageView backButton;
    String userID;
    Spinner colorSpinner, sizeSpinner;
    String[] colorList = {"White", "Black"};
    String[] sizeList = {"Large", "Small"};
    String color, size;
    AddProductsViewModel addProductsViewModel;
    TextView priceText, addProductButton, descriptionText;
    EditText productName;
    int count = 0;

    ImageView pickImageButton;
    private static final int PICK_IMAGE_REQUEST = 1, CAMERA_REQUEST = 1;
    final int IMAGE_REQUEST_CODE = 999;
    private Uri filepath;
    private Bitmap bitmap;
    String imgdata;
    int check = 0, final_check = 0;

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
                priceText.setText(price);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bundle bundle = data.getExtras();

                if (check == 1) {
                    bitmap = (Bitmap) bundle.get("data");
                    check = 0;
                    final_check = 1;
                    pickImageButton.setImageBitmap(bitmap);
                }


            } else if (requestCode == IMAGE_REQUEST_CODE) {
                filepath = data.getData();
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);

                    if (check == 1) {
                        check = 0;
                        final_check = 1;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        pickImageButton.setImageBitmap(bitmap);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_products_fragment, container, false);

        addProductsViewModel = new ViewModelProvider(this).get(AddProductsViewModel.class);

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

        priceText = (TextView) view.findViewById(R.id.priceTextID);
        descriptionText = (TextView) view.findViewById(R.id.descriptionTextID);
        addProductButton = (TextView) view.findViewById(R.id.addProductButtonID);

        pickImageButton = (ImageView) view.findViewById(R.id.pickImageID);

        productName = (EditText) view.findViewById(R.id.productNameID);

        colorSpinner = (Spinner) view.findViewById(R.id.colorSpinnerID);
        sizeSpinner = (Spinner) view.findViewById(R.id.sizeSpinnerID);

        ArrayAdapter colorAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, colorList);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sizeList);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        color = "";
        size = "";

        colorSpinner.setOnItemSelectedListener(this);
        sizeSpinner.setOnItemSelectedListener(this);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = productName.getText().toString().trim();
                String price = priceText.getText().toString().trim();
                String description = descriptionText.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getActivity(), "Empty product name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(description)) {
                        Toast.makeText(getActivity(), "Empty description", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (final_check == 1) {
                        imgdata = imgToString(bitmap);
                    } else {
                        imgdata = "xyz";
                    }
                    addProductsViewModel.getProduct(name, color, size, price, description, imgdata).observe(getViewLifecycleOwner(), new Observer<Add_product_response>() {
                        @Override
                        public void onChanged(Add_product_response add_product_response) {
                            String message = add_product_response.getMessage();

                            if (message.equals("inserted")) {
                                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                                        R.anim.slide_in,  // enter
                                        R.anim.fade_out,  // exit
                                        R.anim.fade_in,   // popEnter
                                        R.anim.slide_out  // popExit
                                ).replace(R.id.frame_container, new Home_fragment()).commit();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } else {

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
                imageSelect();
            }
        });

        return view;
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        String encodeimg = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        return encodeimg;
    }

    private void imageSelect() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (items[i].equals("Camera")) {
                    check = 1;
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[i].equals("Gallery")) {
                    check = 1;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.colorSpinnerID) {
            color = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.sizeSpinnerID) {
            size = parent.getItemAtPosition(position).toString();
        }
        if (!color.isEmpty() && !size.isEmpty())
            price_func(color, size);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}