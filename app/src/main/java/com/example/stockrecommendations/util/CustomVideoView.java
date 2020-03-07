package com.example.stockrecommendations.util;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //我们重新计算高度
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 播放视频
     *
     * @param uri 播放地址
     */

    public void playVideo(Uri uri) {

        if (uri == null) {

            throw new IllegalArgumentException("Uri can not be null");

        }

        /**设置播放路径**/

        setVideoURI(uri);

        /**开始播放**/

        start();


//        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//            @Override
//
//            public void onPrepared(MediaPlayer mp) {
//
// /**设置循环播放**/
//                mp.setLooping(true);
//            }
//
//        });

        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
                /**设置循环播放**/
                mp.setLooping(true);
            }
        });



    }

}
