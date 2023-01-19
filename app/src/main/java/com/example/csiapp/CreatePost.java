package com.example.csiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.util.HashMap;
import java.util.List;

public class CreatePost extends AppCompatActivity {

    EditText title,desc;
    Button save,cancel;
    ImageView img;

    Uri image_uri = null;
    ImageView img2;
    private static final int GALLERY_IMAGE_CODE = 100;
    private static final int CAMERA_IMAGE_CODE = 200;
    ProgressDialog pd;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Add Post");
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        permission();

        desc=findViewById(R.id.desc);
        title=findViewById(R.id.title);
        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        img=findViewById(R.id.coverImg);
        img2 = findViewById(R.id.coverImg2);

        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String d = desc.getText().toString();

                if(TextUtils.isEmpty(t)){
                    title.setError("Title is required");
                }
                else if(TextUtils.isEmpty(d)){
                    desc.setError("Description is required");
                }
                else{
                    uploadData(t,d);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("");
                desc.setText("");
                startActivity(new Intent(CreatePost.this,MainActivity.class));
            }
        });


    }

    private void uploadData(String t, String d) {

        pd.setMessage("Uploading");
        pd.show();

        final String timestamp = String.valueOf(System.currentTimeMillis());

        String filepath =  "Posts/" + "post_"+ timestamp;

        if(img.getDrawable() != null){
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress (Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();


            StorageReference reference = FirebaseStorage.getInstance().getReference().child (filepath);
            reference.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());

                            String downloadUrl = uriTask.getResult().toString();

                            if(uriTask.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();

                                HashMap<String , Object> hashMap = new HashMap<>();

                                hashMap.put("uid",user.getUid());
                                hashMap.put("uEmail",user.getEmail());
                                hashMap.put("pId",timestamp);
                                hashMap.put("pTitle",t);
                                hashMap.put("pImage",downloadUrl);
                                hashMap.put("pDescription",d);
                                hashMap.put("pTime",timestamp);
                                // Putting data in firebase

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                                ref.child(timestamp).setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pd.dismiss();
                                                Toast.makeText(CreatePost.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                                title.setText("");
                                                desc.setText("");
                                                img.setImageURI(null);
                                                image_uri=null;
                                                Log.d("CreatePost", "onSuccess: uploaded"+hashMap);

                                                // Starting Main Activity
                                                startActivity(new Intent(CreatePost.this,MainActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pd.dismiss();
                                                Toast.makeText(CreatePost.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(CreatePost.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
        }
    }

    private void imagePickDialog() {
        String[] options = {"Camera" , "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 1){
                    galleryPick();
                }
                else if(i == 0){
                    cameraPick();
                }
            }
        });

        builder.create().show();
    }

    private void cameraPick() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp desc");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent,CAMERA_IMAGE_CODE);
    }

    private void galleryPick() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_IMAGE_CODE);
    }

    private void permission(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_IMAGE_CODE){
                img.setVisibility(View.VISIBLE);
                image_uri = data.getData();
                img.setImageURI(image_uri);
            }
            if(requestCode == CAMERA_IMAGE_CODE){
                img.setVisibility(View.VISIBLE);
                img.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}