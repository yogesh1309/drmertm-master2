package com.drmertm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.fenchtose.nocropper.BitmapResult;
import com.fenchtose.nocropper.CropInfo;
import com.fenchtose.nocropper.CropMatrix;
import com.fenchtose.nocropper.CropResult;
import com.fenchtose.nocropper.CropState;
import com.fenchtose.nocropper.CropperCallback;
import com.fenchtose.nocropper.CropperView;
import com.fenchtose.nocropper.ScaledCropper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ActivityImgCropping extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_PERMISSION = 22;
    private static final int REQUEST_GALLERY = 21;
    private static final String TAG = "ActivityImgCropping";

    CropperView mImageView;
    CheckBox originalImageCheckbox;
    CheckBox cropAsyncCheckbox;

    private Bitmap originalBitmap;
    private Bitmap mBitmap;
    private boolean isSnappedToCenter = false;

    private int rotationCount = 0;
    private int REQUEST_CAMERA = 0;

    private HashMap<String, CropMatrix> matrixMap = new HashMap<>();
    private String currentFilePath = null;
    int i=0;
    ImageView img_back;
    Context context;
    int key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_img_cropping);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_img_cropping);
        } else {
            Log.i(TAG, "Set landscape mode");
            setContentView(R.layout.activity_img_cropping_landscap);
        }
        try {
            Intent intent = getIntent();
            if (intent!=null)
            {
                key = intent.getIntExtra("key",0);
                System.out.println("key >>>>>>>>>"+key);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        InitView();
        Click();

        //Permission check.
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityImgCropping.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        }
    }

    private void Click() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                startGalleryIntent();
            }
        });

        findViewById(R.id.crop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==0)
                {
                 finish();
                }else {
                    onImageCropClicked();
                }
            }
        });

        findViewById(R.id.rotate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage();
            }
        });

        findViewById(R.id.snap_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snapImage();
            }
        });

        findViewById(R.id.gesture_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleGestures();
            }
        });

        mImageView.setDebug(true);

        mImageView.setGridCallback(new CropperView.GridCallback() {
            @Override
            public boolean onGestureStarted() {
                return true;
            }

            @Override
            public boolean onGestureCompleted() {
                return false;
            }
        });
    }

    private void InitView()
    {
        context = ActivityImgCropping.this;
        img_back = findViewById(R.id.img_back);
        mImageView = findViewById(R.id.imageview);
        originalImageCheckbox = findViewById(R.id.original_checkbox);
        cropAsyncCheckbox = findViewById(R.id.crop_checkbox);
    }
    public void onImageButtonClicked() {
        startGalleryIntent();
    }

    public void onImageCropClicked() {
        if (cropAsyncCheckbox.isChecked()) {
            cropImageAsync();
        } else {
            cropImage();
        }
    }

    public void toggleGestures() {
        boolean enabled = mImageView.isGestureEnabled();
        enabled = !enabled;
        mImageView.setGestureEnabled(enabled);
        Toast.makeText(this, "Gesture " + (enabled ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
    }

    private void loadNewImage(String filePath) {
        this.currentFilePath = filePath;
        rotationCount = 0;
        Log.i(TAG, "load image: " + filePath);
        mBitmap = BitmapFactory.decodeFile(filePath);
        originalBitmap = mBitmap;
        Log.i(TAG, "bitmap: " + mBitmap.getWidth() + " " + mBitmap.getHeight());

        int maxP = Math.max(mBitmap.getWidth(), mBitmap.getHeight());
        float scale1280 = (float)maxP / 1280;
        Log.i(TAG, "scaled: " + scale1280 + " - " + (1/scale1280));

        if (mImageView.getWidth() != 0) {
            mImageView.setMaxZoom(mImageView.getWidth() * 14 / 1280f);
        } else {

            ViewTreeObserver vto = mImageView.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw()
                {
                    mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    mImageView.setMaxZoom(mImageView.getWidth() * 14 / 1280f);
                    return true;
                }
            });

        }

        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int)(mBitmap.getWidth()/scale1280),
                (int)(mBitmap.getHeight()/scale1280), true);

        mImageView.setImageBitmap(mBitmap);
        final CropMatrix matrix = matrixMap.get(filePath);
        if (matrix != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mImageView.setCropMatrix(matrix, true);
                }
            }, 30);
        }
    }

    private void startGalleryIntent() {

        if (currentFilePath != null) {
            matrixMap.put(currentFilePath, mImageView.getCropMatrix());
        }
        else if (!hasGalleryPermission()) {
            askForGalleryPermission();
            return;
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_GALLERY);
        }
    }

    private boolean hasGalleryPermission() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void askForGalleryPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_PERMISSION);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent resultIntent) {
        super.onActivityResult(requestCode, responseCode, resultIntent);

        if (responseCode == RESULT_OK) {
            String absPath = BitmapUtils.getFilePathFromUri(this, resultIntent.getData());
            loadNewImage(absPath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryIntent();
                return;
            }
        }
//        Toast.makeText(this, "Gallery permission not granted", Toast.LENGTH_SHORT).show();
    }

    private void cropImageAsync() {
        try {
            CropState state = mImageView.getCroppedBitmapAsync(new CropperCallback() {
                @Override
                public void onCropped(Bitmap bitmap) {
                    if (bitmap != null) {
                        try {
                            String filepath = Environment.getExternalStorageDirectory()+"/Download"+"/"+System.currentTimeMillis() +".jpg";
                          bitmap = Bitmap.createScaledBitmap(bitmap,2729,2729,true);
//                          bitmap = Bitmap.createScaledBitmap(bitmap,90,90,true);
                            BitmapUtils.writeBitmapToFile(bitmap, new File(filepath), 100);
                            if (filepath.equals(""))
                            {
                                Toast.makeText(context, "Please select image !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intent = new Intent();
                                intent.putExtra("data",filepath);
                                setResult(key,intent);
                                finish();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onOutOfMemoryError() {

                }
            });

            if (state == CropState.FAILURE_GESTURE_IN_PROCESS) {
                Toast.makeText(this, "unable to crop. Gesture in progress", Toast.LENGTH_SHORT).show();
            }

            if (originalImageCheckbox.isChecked()) {
                cropOriginalImageAsync();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void cropImage() {

        try {
            BitmapResult result = mImageView.getCroppedBitmap();

            if (result.getState() == CropState.FAILURE_GESTURE_IN_PROCESS) {
                Toast.makeText(this, "unable to crop. Gesture in progress", Toast.LENGTH_SHORT).show();
                return;
            }

            Bitmap bitmap = result.getBitmap();

            if (bitmap != null) {
                Log.d("Cropper", "crop1 bitmap: " + bitmap.getWidth() + ", " + bitmap.getHeight());
                try {
                    String filepath = Environment.getExternalStorageDirectory()+"/Download"+"/"+System.currentTimeMillis() +".jpg";
                    bitmap = Bitmap.createScaledBitmap(bitmap,2729,2729,true);
                    BitmapUtils.writeBitmapToFile(bitmap, new File(filepath), 100);
                    System.out.println("above filepath >>>>>>>>>"+filepath);
                    if (filepath.equals(""))
                    {
                        Toast.makeText(context, "Please select image !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent();
                        System.out.println("under filepath >>>>>>>>>"+filepath);
                        intent.putExtra("data",filepath);
                        setResult(key,intent);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (originalImageCheckbox.isChecked()) {
                cropOriginalImage();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private ScaledCropper prepareCropForOriginalImage() {
        CropResult result = mImageView.getCropInfo();
        if (result.getCropInfo() == null) {
            return null;
        }
        float scale;
        if (rotationCount % 2 == 0) {
            // same width and height
            scale = (float) originalBitmap.getWidth()/mBitmap.getWidth();
        }
        else {
            // width and height are interchanged
            scale = (float) originalBitmap.getWidth()/mBitmap.getHeight();
        }

        CropInfo cropInfo = result.getCropInfo().rotate90XTimes(mBitmap.getWidth(), mBitmap.getHeight(), rotationCount);
        return new ScaledCropper(cropInfo, originalBitmap, scale);
    }

    private void cropOriginalImage() {
        if (originalBitmap != null) {
            ScaledCropper cropper = prepareCropForOriginalImage();
            if (cropper == null) {
                return;
            }

            Bitmap bitmap = cropper.cropBitmap();
            if (bitmap != null) {
               /* try {
                    BitmapUtils.writeBitmapToFile(bitmap, new File(Environment.getExternalStorageDirectory() + "/crop_test_info_orig.jpg"), 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    private void cropOriginalImageAsync() {
        if (originalBitmap != null) {
            ScaledCropper cropper = prepareCropForOriginalImage();
            if (cropper == null) {
                return;
            }

            cropper.crop(new CropperCallback() {
                @Override
                public void onCropped(Bitmap bitmap) {
                    if (bitmap != null) {
                      /*  try {
                            BitmapUtils.writeBitmapToFile(bitmap, new File(Environment.getExternalStorageDirectory() + "/crop_test_info_orig.jpg"), 100);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            });
        }
    }

    private void rotateImage() {
        if (mBitmap == null) {
            Log.e(TAG, "bitmap is not loaded yet");
            return;
        }

        mBitmap = BitmapUtils.rotateBitmap(mBitmap, 90);
        mImageView.setImageBitmap(mBitmap);
        rotationCount++;
    }

    private void snapImage() {
        if (isSnappedToCenter) {
            mImageView.cropToCenter();
//            mImageView.cropToCenter();
        } else {
            mImageView.fitToCenter();
//            mImageView.fitToCenter();
        }
        isSnappedToCenter = !isSnappedToCenter;
    }
}