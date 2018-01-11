package com.hikmah.dewi.fastprint.view;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hikmah.dewi.fastprint.R;
import com.hikmah.dewi.fastprint.permission.PermissionHelper;
import com.hikmah.dewi.fastprint.config.NoActionBarConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{
    private static int intervalsplash = 5000;
    // Animation
    private PermissionHelper permissionHelper;
    @BindView(R.id.gambar)
    ImageView imageView;
    Animation bounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        NoActionBarConfig noActionBarConfig = new NoActionBarConfig();
        noActionBarConfig.fullScreen(this);
        ButterKnife.bind(this);
        permissionHelper = new PermissionHelper(this);
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        imageView.startAnimation(bounce);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                checkAndRequestPermissions();
                this.finish();

            }

            private void finish() {
                // TODO Auto-generated method stub
            }
        }, intervalsplash);
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for blink animation
        if (animation == bounce) {
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }
    private boolean checkAndRequestPermissions() {
        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
            @Override
            public void onPermissionCheckDone() {
                Intent intent = new Intent(getApplicationContext(), OnBoardingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        permissionHelper.checkAndRequestPermissions();

        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode, permissions, grantResults);
    }
}
