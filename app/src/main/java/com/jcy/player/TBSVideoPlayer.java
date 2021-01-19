package com.jcy.player;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.smtt.sdk.TbsVideo;

class TBSVideoPlayer implements IVideoPlayer {

    private volatile static TBSVideoPlayer mInstance;

    private TBSVideoPlayer() {
    }

    static TBSVideoPlayer getInstance() {
        if (mInstance == null) {
            synchronized (TBSVideoPlayer.class) {
                if (mInstance == null) {
                    mInstance = new TBSVideoPlayer();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void play(Activity activity, String url, String title, String subTitle, int currentPart, String picUrl, int lastPosition) {
        // 腾讯X5在播放前保存历史
        Bundle bundle = new Bundle();
        bundle.putInt("screenMode", 102);
        TbsVideo.openVideo(activity, url, bundle);
    }
}
