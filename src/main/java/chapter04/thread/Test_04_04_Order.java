package chapter04.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * jps:获取进程ID
 * 420 
1093 Test_04_04_Order
1102 Jps

jstack 420（这里的进程ID需要和键入jps得出的ID一致
 *
 */
public class Test_04_04_Order 
{
	public static void main(String[] args)
	{
		new Thread()
		{
			public void run()
			{
				try {
					TimeUnit.SECONDS.sleep(1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}.start();
		
		LockSupport.park();
	}
}
