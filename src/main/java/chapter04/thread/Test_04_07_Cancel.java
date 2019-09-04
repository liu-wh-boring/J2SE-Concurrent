package chapter04.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class Test_04_07_Cancel 
{
	static class Task implements Runnable
	{

		private volatile boolean cancel = false;
		
		@Override
		public void run() {
			while(!cancel && !Thread.currentThread().isInterrupted())
			{
				System.out.println("-------------------");
			}
		}
	}
	
	/**
	 * 通过标识取消线程
	 * @throws InterruptedException 
	 */
	@Test
	public void test() throws InterruptedException
	{
		Task t = new Task();
		new Thread(t).start();
		TimeUnit.SECONDS.sleep(3);
		t.cancel = true;
		LockSupport.park();
	}
	
	/**
	 * 通过中断方式取消线程
	 * @throws InterruptedException
	 */
	@Test
	public void test1() throws InterruptedException
	{
		Thread t = new Thread(new Task());
		t.start();
		TimeUnit.SECONDS.sleep(3);
		
		t.interrupt();
		LockSupport.park();
	}
	
	
}
