package tw.jason.app.helloworld.myasynctask;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  MyAsyncTask myAsyncTask;
    private TextView tv,tv2;
    private MyServiceConnection myServiceConnection;
    private SeekBar seekBar;
    private  MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
        tv2 = (TextView)findViewById(R.id.tv2);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    Intent it = new Intent(MainActivity.this, MyService2.class);
                    it.putExtra("seekto", i);
                    startService(it);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


                myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("brad");
        registerReceiver(myReceiver,filter);

//        Intent it = new Intent(this,MyService1.class);
//        myServiceConnection = new MyServiceConnection();
//        bindService(it,myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void finish() {
        unregisterReceiver(myReceiver);
        super.finish();
    }

    private class MyServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public void test1(View view){
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Brad","OK","III","Game");

    }

    public void test2(View view){
        tv.setText("Lottery:"+(int)(Math.random()*49+1));
    }
    public void test3(View view) {
        if (myAsyncTask != null && !myAsyncTask.isCancelled()) {
            myAsyncTask.cancel(true);

        }
    }
    public void test4(View view){
        Intent it = new Intent(this,MyService2.class);
        it.putExtra("start",true);
        startService(it);
    }
    public void test5(View view){
        Intent it = new Intent(this,MyService2.class);
        it.putExtra("pause",true);
        startService(it);
    }
    public void test6(View view){
        Intent it = new Intent(this,MyService2.class);
        stopService(it);
    }


    private class MyAsyncTask extends AsyncTask<String,Integer,String>{
        int i;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("brad","onPreExecute");
        }

        @Override
        protected void onPostExecute(String ret) {
            super.onPostExecute(ret);
            //Log.i("brad","onPostExecute");
            tv.setText(ret);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Log.i("brad","onProgressUpdate");
            tv2.setText(values[0]+":"+values[1]+":"+values[2]);



        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
            //Log.i("brad","onCancelled(Void void)");
            tv.setText(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("brad","onCancelled");
        }

        @Override
        protected String doInBackground(String... names) {
            String result = "OK";
            //Log.i("brad","doInBackground");
            for(String name : names){
                i++;
                Log.i("brad",name);
                publishProgress(i,i*10,i*100);
                if(isCancelled()){
                    result = "NOT OK";
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }

            return result;
        }
    }
    private class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int len = intent.getIntExtra("len",-1);
            int now = intent.getIntExtra("now",-1);
            if(len>0){
                seekBar.setMax(len);
            }
            if(now>0){
                seekBar.setProgress(now);
            }
            //Log.i("brad","onReceive");
        }
    }
}
