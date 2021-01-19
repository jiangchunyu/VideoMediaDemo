package com.edu.videomediademo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.jcy.player.IVideoPlayer;
import com.jcy.player.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    private String mp4Url = "http://ggkkmuup9wuugp6ep8d.exp.bcevod.com/mda-ma2pnjmbja1pmz0d/mda-ma2pnjmbja1pmz0d.mp4";
    private String m3u8Url = "https://m3u8-cache.ckflv.cn:203/qq.m3u8?vid=m0017spc9ej&vkey=OTdBdWVyeEtSOTI2dDVEWGk0MG1GPTNEaXJBeWtyeHhPdGotdHR3emw4VkpNNW1iYTcwR2MzVWxk.m3u8";
    private String mkvUrl = "https://meet1-1251849728.cos.ap-beijing-1.myqcloud.com/%E8%A7%86%E9%A2%91/%E5%91%A8%E6%B7%B1-%E6%9D%A5%E4%B8%8D%E5%8F%8A%E5%8B%87%E6%95%A2-%E5%9B%BD%E8%AF%AD-%E6%B5%81%E8%A1%8C.mkv";

    private Button btn_play;
    private Spinner sp_video_player_type;
    private Spinner sp_video_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_play = findViewById(R.id.btn_play);
        sp_video_player_type = findViewById(R.id.sp_video_player_type);
        sp_video_type = findViewById(R.id.sp_video_type);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoType = (String) sp_video_type.getSelectedItem();
                String url = "";
                if (videoType.equals("MP4")) {
                    url = mp4Url;
                } else if (videoType.equals("m3u8")) {
                    url = m3u8Url;
                } else if (videoType.equals("mkv")) {
                    url = mkvUrl;
                }

                int type = sp_video_player_type.getSelectedItemPosition();
                IVideoPlayer player = VideoPlayer.getVideoPlayerByType(type);
                player.play(MainActivity.this, url, "使徒行者", "正品", 0, "", 0);
            }
        });
    }
}