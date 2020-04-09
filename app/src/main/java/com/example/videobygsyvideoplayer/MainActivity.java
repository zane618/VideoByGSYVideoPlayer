package com.example.videobygsyvideoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_zane";

    private StandardGSYVideoPlayer player;
    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = findViewById(R.id.video_player);
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        player.setUp(source1, true, "啊啊啊");

        //隐藏title
        player.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        player.getBackButton().setVisibility(View.GONE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, player);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        player.getFullscreenButton().setOnClickListener(v ->
                orientationUtils.resolveByClick());
        //设置返回键
        player.getBackButton().setOnClickListener(v -> onBackPressed());

        player.setIsTouchWiget(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            player.getFullscreenButton().performClick();
            return;
        }
        //释放所有,应该是点击事件
        player.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
