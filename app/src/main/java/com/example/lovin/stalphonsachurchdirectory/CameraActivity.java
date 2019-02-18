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

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 2;

    private static final String TAG = "CameraActivityTAG";
    private ImageView imageView;

    Button capture, finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo_screen);
        Log.d(TAG, "Inside capture Started ");
        init();



    }
    public void init(){


        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        capture = findViewById(R.id.pictureButton);
        finished = findViewById(R.id.finishButton);
    }

    public void captureImage(View view){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                TransferData.bitmap = null;
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
        onBackPressed();
      //  Intent toAddScreen = new Intent();
      //  toAddScreen.setClass(this.getApplicationContext(), AddActivity.class);
      //  startActivity(toAddScreen);


        //finishActivity(1);


    }

}


