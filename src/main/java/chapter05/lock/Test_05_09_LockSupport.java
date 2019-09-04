package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class Test_05_09_LockSupport 
{
		/**
		 * 测试线程的阻塞和唤醒
		 */
		@Test
		public void test() throws InterruptedException
		{
				//阻塞一个线程
				Thread zhangfei = new Thread(()->{
					String name = Thread.currentThread().getName();
					System.out.println(name+"->"+"阻塞");
					LockSupport.park();
					System.out.println(name+"->"+"唤醒");
				},"张飞");
				zhangfei.start();
				
				TimeUnit.SECONDS.sleep(30);
				//唤醒线程
				LockSupport.unpark(zhangfei);
				
				//避免主线程直接推出
				LockSupport.park();
		}
		
		/**
		 * 测试park方法的中断敏感
		 * 中断敏感，但是不会抛出异常
		 * @throws InterruptedException 
		 */
		@Test
		public void test2() throws InterruptedException
		{
			Thread zhangfei = new Thread(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name+"->"+"阻塞");
				try
				{
					LockSupport.park();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				System.out.println(name+"->"+"唤醒");
			},"张飞");
			zhangfei.start();
			TimeUnit.SECONDS.sleep(3);
			zhangfei.interrupt();
			
			//避免主线程直接推出
			LockSupport.park();
		}
		
		/**
		 * 带沉睡时间，自动唤醒
		 */
		@Test
		public void test3()
		{
			Thread zhangfei = new Thread(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name+"->"+"阻塞");
				LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(10));//⚠️：超时等待不是park（10000）；
				System.out.println(name+"->"+"唤醒");
			},"张飞");
			zhangfei.start();
			//避免主线程直接推出
			LockSupport.park();
		}
		
		/**
		 * 超时沉睡
		 */
		@Test
		public void test4()
		{
			Thread zhangfei = new Thread(()->{
				String name = Thread.currentThread().getName();
				System.out.println(name+"->"+"阻塞");
				//deadline the absolute time, in milliseconds from the Epoch, to wait until
				long now = System.currentTimeMillis();
				LockSupport.parkUntil(now+TimeUnit.SECONDS.toMillis(10));
				System.out.println(System.currentTimeMillis()-now);
				System.out.println(name+"->"+"唤醒");
			},"张飞");
			zhangfei.start();
			//避免主线程直接推出
			LockSupport.park();
		}
}
