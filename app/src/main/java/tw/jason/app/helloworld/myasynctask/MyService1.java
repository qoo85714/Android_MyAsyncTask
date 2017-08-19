package tw.jason.app.helloworld.myasynctask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService1 extends Service {
    public MyService1() {
        Log.i("brad","MyService1()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("brad","onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("brad","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("brad","onDestroy");
    }
}
