package tw.jason.app.helloworld.myasynctask;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService2 extends Service {
    private MediaPlayer mp;
    private Timer timer;

    public MyService2() {
        Log.i("brad", "MySrvice2()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("brad", "onBind2");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 200);
        mp = MediaPlayer.create(this, R.raw.frozen);

        int len = mp.getDuration();
        Intent it = new Intent("brad");
        it.putExtra("len", len);
        sendBroadcast(it);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isStart = intent.getBooleanExtra("start", false);
        boolean isPause = intent.getBooleanExtra("pause", false);
        int seekto = intent.getIntExtra("seekto", -1);

        if (isStart && mp != null && !mp.isPlaying()){
            mp.start();
        }
        if (isPause && mp != null && mp.isPlaying()){
            mp.pause();
        }
        if (seekto >= 0 && mp != null){
            mp.seekTo(seekto);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            if (mp != null && mp.isPlaying()){
                int now = mp.getCurrentPosition();
                Intent it = new Intent("brad");
                it.putExtra("now", now);
                sendBroadcast(it);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null){
            if (mp.isPlaying()) mp.stop();
            mp.release();
            //mp.reset();
            mp = null;
        }
        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}
