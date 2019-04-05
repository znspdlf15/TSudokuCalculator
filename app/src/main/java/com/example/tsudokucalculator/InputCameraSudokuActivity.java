package com.example.tsudokucalculator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InputCameraSudokuActivity extends AppCompatActivity {
    private TextureView mCameraTextureView;
    private Preview mPreview;

    Activity mainActivity = this;

    private static final String TAG = "InputCameraSudokuActivity";

    static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_camera_sudoku);

        mCameraTextureView = findViewById(R.id.cameraView);
        mPreview = new Preview(this, mCameraTextureView);

        Button camera_bt = findViewById(R.id.camera_button);

        camera_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreview.takePicture();

                Intent intent = new Intent(getApplicationContext(), InputCameraSudokuActivity.class);
//                intent.putExtra("image_parameter", image);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            mCameraTextureView = findViewById(R.id.cameraView);
                            mPreview = new Preview(mainActivity, mCameraTextureView);
                            mPreview.openCamera();
                            Log.d(TAG,"mPreview set");
                        } else {
                            Toast.makeText(this,"Should have camera permission to run", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }
}
