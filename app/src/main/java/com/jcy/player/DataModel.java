package com.jcy.player;

import android.content.Context;

import com.edu.videomediademo.Const;

public class DataModel {

    private volatile static DataModel mInstance;

    private DataModel() {
    }

    static DataModel getInstance() {
        if (mInstance == null) {
            synchronized (DataModel.class) {
                if (mInstance == null) {
                    mInstance = new DataModel();
                }
            }
        }
        return mInstance;
    }

    private static final String APP_PREFERENCE = "app_preference";

    private static final String PREFERENCE_KEY_VIDEO_ENGINE = "preference_key_video_engine";
    private static final String PREFERENCE_KEY_PLAYER_ENGINE = "preference_key_player_engine";


    public void setVideoEngine(Context context, String videoEngine) {
        save(context, PREFERENCE_KEY_VIDEO_ENGINE, videoEngine);
    }

    public String getVideoEngine(Context context) {
        return get(context, PREFERENCE_KEY_VIDEO_ENGINE, Const.DEFAULT_VIDEO);
    }

    public void setPlayerEngine(Context context, String playerEngine) {
        save(context, PREFERENCE_KEY_PLAYER_ENGINE, playerEngine);
    }

    public String getPlayerEngine(Context context) {
        return get(context, PREFERENCE_KEY_PLAYER_ENGINE, Const.DEFAULT_PLAY);
//        return Const.PLAY_4;
    }


    private void save(Context context, String key, String value) {
        context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    private String get(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE).getString(key, defaultValue);
    }
}
