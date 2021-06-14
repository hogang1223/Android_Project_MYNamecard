package com.aoslec.mynamecard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aoslec.mynamecard.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class imgTest extends AppCompatActivity {

    final int MY_PERMISSION_REQUEST_CODE = 100;
    int APIVersion = Build.VERSION.SDK_INT;
    private Camera mCamera;
    private ImageView mImage;
    private boolean mInProgress;
    byte[] data;
    DataOutputStream dos;
    ImageView view;
    SurfaceView surface;
    Button btnCapture, btnSave;
    File saveFile = new File("http://192.168.219.105:8080/first/img01.jpg");
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_test);

        mImage = findViewById(R.id.image_view);
        surface = findViewById(R.id.surface_view);
        btnCapture = findViewById(R.id.btnCapture);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShutterListener.onShutter();
            }
        });

        SurfaceHolder holder = surface.getHolder();
        view = findViewById(R.id.image_view);

        // SurfaceView 리스너를 등록
        holder.addCallback(mSurfaceListener);

        // 외부 버퍼를 사용하도록 설정
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            switch(keyCode) {
                case KeyEvent.KEYCODE_CAMERA:
                    //videoPreview.onCapture(settings);
                    mShutterListener.onShutter();
                    /* ... */
                    return true;
            }
        }
        return false;
    }

    // 권한처리
    private boolean checkCAMERAPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private SurfaceHolder.Callback mSurfaceListener = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {

            if(APIVersion>=Build.VERSION_CODES.M){
                if(checkCAMERAPermission()){
                    mCamera = Camera.open();
                    mCamera.setDisplayOrientation(90);
                    Log.v("CameraTest", "Camera opened");
                }else{
                    ActivityCompat.requestPermissions(imgTest.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CODE);
                }
            }
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            // 미리보기 크기를 설정
//            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setPreviewSize(width, height);
//            mCamera.setParameters(parameters);
            mCamera.startPreview();
            Log.v("CameraTest", "Camera preview started");
        }
        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            // SurfaceView가 삭제되는 시간에 카메라를 개방
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            Log.v("CameraTest", "Camera released");
        }
    };

    private Camera.ShutterCallback mShutterListener = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            Log.v("CameraTest", "onShutter");
            if (mCamera != null && mInProgress == false) {
                // 이미지 검색을 시작한다. 리스너 설정
                mCamera.takePicture(
                        mShutterListener,  // 셔터 후
                        null, // Raw 이미지 생성 후
                        mPicutureListener); // JPE 이미지 생성 후
                mInProgress = true;
            }
        }
    };

    private Camera.PictureCallback mPicutureListener = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.v("CameraTest", "Picture Taken");
            if (data != null) {
                Log.v("CameraTest", "JPEG Picture Taken");

                //  적용할 옵션이 있는 경우 BitmapFactory클래스의 Options()
                //  메서드로 옵션객체를 만들어 값을 설정하며
                //  이렇게 만들어진 옵션을 Bitmap 객체를 만들때 네번째
                //  아규먼트로 사용한다.
                //
                //  처리하는 이미지의 크기를 축소
                //  BitmapFactory.Options options =
                //      new BitmapFactory.Options();
                //  options.inSampleSize = IN_SAMPLE_SIZE;
                imgTest.this.data=data;
                Bitmap bitmap =
                        BitmapFactory.decodeByteArray(data,
                                0,
                                data.length,
                                null);
                //이미지 뷰 이미지 설정
                mImage.setImageBitmap(bitmap);
                doFileUpload();  //서버에 이미지를 전송하는 메서드 호출
                Toast.makeText(imgTest.this, "서버에 파일을 성공적으로 전송하였습니다", Toast.LENGTH_LONG).show();
                // 정지된 프리뷰를 재개
                camera.startPreview();
                mInProgress = false;
            }
        }
    };

    public void doFileUpload(){
        try {
            URL url = new URL("http://192.168.219.105:8080/first/imageTest.jsp");
            Log.v("CameraTest", "url : " + url);
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";


            // open connection
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setConnectTimeout(10000);
            con.setDoInput(true); //input 허용
            con.setDoOutput(true);  // output 허용
            con.setUseCaches(false);   // cache copy를 허용하지 않는다.
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


            // write data
            Log.v("CameraTest", "write data" );
            DataOutputStream dos =
                    new DataOutputStream(con.getOutputStream());
            Log.v("CameraTest", "Open OutputStream" );
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            // 파일 전송시 파라메터명은 file1 파일명은 camera.jpg로 설정하여 전송
            dos.writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\"camera.jpg\"" +
                    lineEnd);

            dos.writeBytes(lineEnd);
            dos.write(data,0,data.length);
            Log.v("CameraTest",data.length+"bytes written" );
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            dos.flush(); // finish upload...

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("CameraTest", "exception " + e.getMessage());
            // TODO: handle exception
        }
        Log.v("CameraTest", data.length+"bytes written successed ... finish!!" );
        try {
            dos.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}