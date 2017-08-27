package com.example.achintya.foodorderingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFood extends AppCompatActivity {

    private ImageButton foodImage;
    private static final int GALLREQ = 1;

    private EditText name, desc, price;

    private Uri uri = null;

    private StorageReference storageReference = null;

    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        name = (EditText)findViewById(R.id.itemName);
        price = (EditText)findViewById(R.id.itemPrice);
        desc = (EditText)findViewById(R.id.itemDesc);

        storageReference = FirebaseStorage.getInstance().getReference();

        mRef = FirebaseDatabase.getInstance().getReference("Item");

    }

    public void imageButtonClicked(View view){

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent, GALLREQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLREQ && resultCode == RESULT_OK){

            uri = data.getData();
            foodImage = (ImageButton)findViewById(R.id.foodImageButton);

            foodImage.setImageURI(uri);
        }
    }

    public void addItemButtonClick(View view){

        final String name_text = name.getText().toString().trim();
        final String desc_text = desc.getText().toString().trim();
        final String price_text = price.getText().toString().trim();

        if(!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(desc_text) && !TextUtils.isEmpty(price_text)){

            StorageReference filePath = storageReference.child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(AddFood.this, "Image Uploaded", Toast.LENGTH_LONG).show();

                    final DatabaseReference newPost = mRef.push();
                    newPost.child("name").setValue(name_text);
                    newPost.child("desc").setValue(desc_text);
                    newPost.child("price").setValue(price_text);
                    newPost.child("image").setValue(downloadUrl.toString());

                }
            });

        }

    }
}
