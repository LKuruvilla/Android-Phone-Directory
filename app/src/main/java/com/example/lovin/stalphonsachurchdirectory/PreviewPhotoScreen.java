package com.example.lovin.stalphonsachurchdirectory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PreviewPhotoScreen extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final String TAG = "PreviewPhotoScreen";
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Button capture, finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo_screen);
        Log.d(TAG, "Inside capture Started ");

          TransferData.bitmap = null;
       this.imageView = (ImageView)this.findViewById(R.id.imageView);
        capture = findViewById(R.id.pictureButton);
        finished = findViewById(R.id.finishButton);
        try {
            capture.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Inside capture if ");
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Log.d(TAG, "Inside capture else");

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            });

        }catch (Exception e)
        {
            Log.d(TAG,"Camera failure: "+e.getLocalizedMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Inside capture start request permission");

        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Inside capture permission granted ");

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Log.d(TAG, "Inside capture permission deneied ");

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "Inside capture onActivity Result ");

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                TransferData.bitmap =photo;
                capture.setText("ReCapture Image");
                finished.setVisibility(View.VISIBLE);

            }
    }
    public void finishedPreview(View view)
    {
        Log.d(TAG, "Inside capture finish preview ");

        Intent toAddScreen = new Intent();
        toAddScreen.setClass(this.getApplicationContext(), AddScreen.class);
        startActivity(toAddScreen);
        finish();


    }

}


