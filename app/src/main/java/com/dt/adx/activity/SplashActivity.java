package com.dt.adx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dt.adx.R;
import com.dt.adx.utils.FoxBaseToastUtils;
import com.mediamain.android.adx.base.FoxADXADBean;
import com.mediamain.android.adx.response.BidResponse;
import com.mediamain.android.adx.view.splash.FoxADXShView;
import com.mediamain.android.adx.view.splash.FoxADXSplashAd;
import com.mediamain.android.adx.view.splash.FoxADXSplashHolder;
import com.mediamain.android.view.bean.MessageData;
import com.mediamain.android.view.holder.FoxNativeAdHelper;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName()+"====";

    private FrameLayout mContainer;
    private FoxADXSplashHolder adxSplashHolder;
    private FoxADXSplashHolder.LoadAdListener mSplashAdListener;
    private int slotId =  421090;
    private String userId;
    private FoxADXShView foxADXShView;
    private int price =100;
    private FoxADXSplashAd mFoxADXSplashAd;
    private FoxADXADBean mFoxADXADBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_splash);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        adxSplashHolder = FoxNativeAdHelper.getADXSplashHolder();
        findViewById(R.id.btnRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAd();
            }
        });
    }

    private void getAd() {
        adxSplashHolder.loadAd(slotId, userId, new FoxADXSplashHolder.LoadAdListener() {
            @Override
            public void servingSuccessResponse(BidResponse bidResponse) {

            }

            @Override
            public void onError(int code, String msg) {
            }

            @Override
            public void onAdGetSuccess(FoxADXSplashAd foxADXSplashAd) {
                if (foxADXSplashAd!=null){
                    foxADXShView = (FoxADXShView) foxADXSplashAd.getView();
                    //获取竞价价格
                    foxADXSplashAd.getECPM();
                }

            }

            @Override
            public void onAdCacheSuccess(FoxADXADBean foxADXADBean) {
                mFoxADXADBean = foxADXADBean;
                if (mFoxADXADBean!=null){
                    ViewGroup contentView = findViewById(android.R.id.content);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    contentView.removeAllViews();
                    contentView.addView(foxADXShView, params);
                    mFoxADXADBean.setPrice(price);
                    foxADXShView.setAdListener(new FoxADXSplashAd.LoadAdInteractionListener() {
                        @Override
                        public void onAdLoadFailed() {

                        }

                        @Override
                        public void onAdLoadSuccess() {

                        }

                        @Override
                        public void onAdClick() {

                        }

                        @Override
                        public void onAdExposure() {

                        }

                        @Override
                        public void onAdTimeOut() {
                               jumpMain();
                        }

                        @Override
                        public void onAdJumpClick() {
                            jumpMain();
                        }

                        @Override
                        public void onAdActivityClose(String s) {
                            jumpMain();
                        }

                        @Override
                        public void onAdMessage(MessageData messageData) {

                        }

                        @Override
                        public void servingSuccessResponse(BidResponse bidResponse) {

                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    foxADXShView.showAd(SplashActivity.this,mFoxADXADBean);
                }
                FoxBaseToastUtils.showShort("onAdCacheSuccess ");
            }

            @Override
            public void onAdCacheCancel(FoxADXADBean foxADXADBean) {

            }

            @Override
            public void onAdCacheFail(FoxADXADBean foxADXADBean) {

            }

            @Override
            public void onAdCacheEnd(FoxADXADBean foxADXADBean) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (adxSplashHolder != null) {
            adxSplashHolder.destroy();
        }
        if (foxADXShView!=null){
            foxADXShView.destroy();
        }
        super.onDestroy();
    }

    /**
     * 跳转主页
     */
    private void jumpMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}