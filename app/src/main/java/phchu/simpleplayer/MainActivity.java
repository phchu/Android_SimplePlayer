package phchu.simpleplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private int stopPosition;
    private VideoView videoView;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) this.findViewById(R.id.video_view);
        videoPath = "android.resource://" + getPackageName() + "/" + R.raw
                .google_arts_and_culture;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        final GestureDetector gestureDetector = new GestureDetector(this, gestureListener);
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle params = getIntent().getExtras();
        if(null != params){
            Log.e(TAG, "VideoView intent stop position: " + stopPosition);
            videoPath = params.getString("videoPath");
            stopPosition = params.getInt("stopPosition");
            videoView.seekTo(stopPosition);
            videoView.start();
        }
    }

    final GestureDetector.SimpleOnGestureListener gestureListener =
            new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            Log.e(TAG, "onSingleTapUp");
            return true;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e(TAG, "onSingleTapConfirmed");
            videoView.pause();
            stopPosition = videoView.getCurrentPosition();
            Intent intent = new Intent(MainActivity.this, FullscreenVideoActivity.class);
            intent.putExtra("videoPath", videoPath);
            intent.putExtra("stopPosition", stopPosition);
            startActivity(intent);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            Log.e(TAG, "onLongPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e(TAG, "onDoubleTap");
            return super.onDoubleTap(e);
        }
    };
}
