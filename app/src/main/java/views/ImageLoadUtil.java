package views;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ImageLoadUtil {
private static ImageLoadUtil ilu;
private Handler Uihandler,backhandler;
private Executor tpe;
private int num;
private Thread backthread;

private LinkedBlockingDeque<Runnable> lbd;
private ImageLoadUtil(int n) {
	num=n;
	lbd=new LinkedBlockingDeque<Runnable>();
	Uihandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.d("handlemessage", msg.what+"");
		}
	};
	backthread=new Thread(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Looper.prepare();
			backhandler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					switch (msg.what) {
					case 0:
						Runnable r=getRunnable(msg.arg1);
						try {
							lbd.putLast(r);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						this.sendEmptyMessage(1);
						break;

					case 1:
						try {
							tpe.execute(lbd.takeLast());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					
				}
			};
			
			Looper.loop();
		}
	};
	tpe=Executors.newFixedThreadPool(num);
	backthread.start();
}
public static ImageLoadUtil getinstance(int n) {
	if(ilu==null){
		ilu=new ImageLoadUtil(n);
	}
	return ilu;
}
public void refresh(int n ) {
	Message m=Message.obtain();
	m.what=0;
	m.arg1=n;
	 backhandler.sendMessage(m);
	 Log.d("onclick", n+"");
}
public Runnable getRunnable(final int n ) {
	Runnable r=new Runnable() {
		@Override
		public void run() {
          try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Uihandler.sendEmptyMessage(n);
		}
	};
	return r;
}
}
