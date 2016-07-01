package phchu.simpleplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class FullscreenVideoActivity extends Activity {
    private String TAG = "FullscreenVideoActivity";
    private int stopPosition;
    private VideoView videoView;
    private MediaController mc;
    private String videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);
        Bundle params = getIntent().getExtras();
        videoPath = params.getString("videoPath");
        stopPosition = params.getInt("stopPosition");
        mc = new MediaController(this);
        videoView = (VideoView) this.findViewById(R.id.fullscreen_video_view);
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(videoPath));
        Log.e(TAG, "FullscreenVideoView intent stop position: " + stopPosition);
        videoView.seekTo(stopPosition);
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoView.pause();
        stopPosition = videoView.getCurrentPosition();
        Intent intent = new Intent(FullscreenVideoActivity.this, MainActivity.class);
        intent.putExtra("videoPath", videoPath);
        intent.putExtra("stopPosition", stopPosition);
        startActivity(intent);
    }
}
