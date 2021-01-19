package com.jcy.player;

import android.app.Activity;

public interface IVideoPlayer {

    void play(Activity activity, String url, String title, String subtitle, int currentPart, String picUrl, int lastPosition);
}
