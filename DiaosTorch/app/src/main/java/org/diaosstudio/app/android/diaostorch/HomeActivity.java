package org.diaosstudio.app.android.diaostorch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.FacebookSdk;
import com.facebook.ads.*;

import com.facebook.appevents.AppEventsLogger;

import org.diaosstudio.app.android.diaostorch.functions.Light;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    AdView mAdView;
    private ViewGroup parentView ;
    private View mTorchLayout ;
    private ImageView mTorchBtn , mAllFunctionBtn;
    private boolean isTorchOn;
    Light mLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_home);

        if(mLight == null){
            mLight = new Light(this);
        }

        initViews();
    }

    private void initViews() {
        parentView = (RelativeLayout)findViewById(R.id.parent_view);

        // Instantiate an AdView view
        mAdView = new AdView(this, "1110391875638905_1110481832296576", AdSize.BANNER_HEIGHT_50);
        // Add the ad view to your activity layout
        parentView.addView(mAdView);

        // Request to load an ad
        mAdView.loadAd();

        mTorchLayout = findViewById(R.id.torch_background);
        mTorchBtn = (ImageView)findViewById(R.id.torch_btn);
        mAllFunctionBtn = (ImageView)findViewById(R.id.all_function_btn);
        mTorchBtn.setOnClickListener(this);
        mAllFunctionBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == mTorchBtn){
            torchToggle();
        }else if(view == mAllFunctionBtn){
            Intent i = new Intent(HomeActivity.this , AllFunctionActivity.class);
            startActivity(i);
        }
    }

    private void torchToggle() {
        if(isTorchOn){
            closeFlashlight();
            mTorchLayout.setBackgroundColor(Color.BLACK);
            isTorchOn = false ;
        }else{
            openFlashlight();
            mTorchLayout.setBackgroundColor(Color.WHITE);
            isTorchOn = true ;
        }
    }

    protected void openFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) mTorchBtn
                .getDrawable();
        drawable.startTransition(200);
        mTorchBtn.setTag(true);
        mLight.turnOn();

    }

//     关闭闪光灯
    protected void closeFlashlight() {
        TransitionDrawable drawable = (TransitionDrawable) mTorchBtn
                .getDrawable();
        if (((Boolean) mTorchBtn.getTag())) {
            drawable.reverseTransition(200);
            mTorchBtn.setTag(false);
            mLight.turnOff();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this,getResources().getString(R.string.app_id));
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }


//    private void torchToggle() {
//        if(isTorchOn){
//            closeFlashlight();
//            mTorchLayout.setBackgroundColor(Color.BLACK);
//            isTorchOn = false ;
//        }else{
//            openFlashlight();
//            mTorchLayout.setBackgroundColor(Color.WHITE);
//            isTorchOn = true ;
//        }
//    }
//
//    // 打开闪光灯
//    protected void openFlashlight() {
//        TransitionDrawable drawable = (TransitionDrawable) mTorchBtn
//                .getDrawable();
//        drawable.startTransition(200);
//        mTorchBtn.setTag(true);
//
//        try {
//            mCamera = Camera.open();
//            int textureId = 0;
//            mCamera.setPreviewTexture(new SurfaceTexture(textureId));
//            mCamera.startPreview();
//
//            mParameters = mCamera.getParameters();
//
//            mParameters.setFlashMode(mParameters.FLASH_MODE_TORCH);
//            mCamera.setParameters(mParameters);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
//
//    // 关闭闪光灯
//    protected void closeFlashlight() {
//        TransitionDrawable drawable = (TransitionDrawable) mTorchBtn
//                .getDrawable();
//        if (((Boolean) mTorchBtn.getTag())) {
//            drawable.reverseTransition(200);
//            mTorchBtn.setTag(false);
//            if (mCamera != null) {
//                mParameters = mCamera.getParameters();
//                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                mCamera.setParameters(mParameters);
//                mCamera.stopPreview();
//                mCamera.release();
//                mCamera = null;
//
//            }
//        }
//    }
}
