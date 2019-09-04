package chapter04.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class Test_04_03_ThreadState 
{
	
	/**
	 * 初始状态(NEW):还未开始
	 * Thread state for a thread which has not yet started.
	 */
	@Test
	public void testNew()
	{
		Thread t = new Thread();
		System.out.println(t.getState());
	}
	
	/**
	 * 运行状态(RUNNABLE)：java中将就绪和运行都归纳到运行状态
	 * 就绪是等待获取时间片
	 */
	@Test
	public void testRunnable()
	{
		Thread t = new Thread(()->{
			while(true);
		}) ;
		t.start();
		System.out.println(t.getState());
	}
	
	/**
	 * 阻塞状态(BLOCKED):线程阻塞于锁
	 * Thread state for a thread blocked waiting for a monitor lock
	 */
	@Test
	public void testBlocked()
	{
		Object o = new Object();
		
		Thread t = new Thread(()->{
			synchronized(o)
			{
				while(true);
			}
		}) ;
		t.start();
		
		Thread t2 = new Thread(()->{
			synchronized(o)
			{
				while(true);
			}
		}) ;
		t2.start();
		
		System.out.println(t.getState());
		System.out.println(t2.getState());
		LockSupport.park();
	}
	
	/**
	 * 等待状态(WAITING)：当前线程需要其他线程作出一定的动作
	 * @throws InterruptedException 
	 */
	@Test
	public void testWaiting() throws InterruptedException
	{
		Thread t1 = new Thread(()->{
			synchronized(this)
			{
				try {
					this.wait();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}) ;
		t1.start();
		
		Thread t2 = new Thread(()->{
			try 
			{
				ReentrantLock lock = new ReentrantLock();
				Condition condition = lock.newCondition();
				lock.lock();
				condition.await();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}) ;
		t2.start();
		
		Thread t3 = new Thread(()->{
			LockSupport.park();
		}) ;
		t3.start();
		
		Thread t4 = new Thread(()->{
				try 
				{
					t3.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}) ;
		t4.start();
		
		TimeUnit.SECONDS.sleep(3);
		System.out.println("wait="+t1.getState());
		System.out.println("await="+t2.getState());
		System.out.println("park="+t3.getState());
		System.out.println("join="+t4.getState());
		LockSupport.park();
	}
	
	/**
	 * 超时等待(TIMED_WAITING):等到一定时间自行结束
	 */
	@Test
	public void testTimeWaiting() throws InterruptedException
	{
		Thread t1 = new Thread(()->{
			synchronized(this)
			{
				try {
					this.wait(10000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}) ;
		t1.start();
		
		Thread t2 = new Thread(()->{
			try 
			{
				ReentrantLock lock = new ReentrantLock();
				Condition condition = lock.newCondition();
				lock.lock();
				condition.await(10000,TimeUnit.HOURS);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}) ;
		t2.start();
		
		
		Thread t3 = new Thread(()->{
			LockSupport.parkNanos(10000000000000L);
		}) ;
		t3.start();
		
		Thread t4 = new Thread(()->{
			try 
			{
				TimeUnit.SECONDS.sleep(1000000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}) ;
		t4.start();
		
		TimeUnit.SECONDS.sleep(3);
		System.out.println("wait="+t1.getState());
		System.out.println("await="+t2.getState());
		System.out.println("park="+t3.getState());
		System.out.println("sleep="+t4.getState());
		LockSupport.park();
	}
	
	/**
	 * 终止状态(TERMINATED)：线程执行完毕
	 * @throws InterruptedException 
	 */
	@Test
	public void testTermiated() throws InterruptedException
	{
		Thread t = new Thread();
		t.start();
		
		TimeUnit.SECONDS.sleep(10);
		System.out.println(t.getState());
		
		LockSupport.park();
	}
	
	/**
	 * A thread is alive if it has been started and has not yet died
	 */
	@Test
	public void testIsAlive()
	{
		System.out.println(Thread.currentThread().isAlive());
	}
}
