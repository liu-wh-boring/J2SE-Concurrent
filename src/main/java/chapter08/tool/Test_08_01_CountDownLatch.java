package chapter08.tool;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch允许一个或者多个线程等待其他线程完成工作
 *
 */
public class Test_08_01_CountDownLatch 
{
	/**
	 * 所有线程执行结束,才执行结论
	 * @throws InterruptedException 
	 */
	@Test
	public void testJoin() throws InterruptedException
	{
		Thread actor = new Thread(new RunnableImpl(),"男主角");
		actor.start();
		
		Thread actress = new Thread(new RunnableImpl(),"女主角");
		actress.start();
		
		//当前线程沉睡
		actor.join();
		actress.join();
		System.out.println("电影开拍");
		
	}
	
	@Test
	public void testCountDownLatch() throws InterruptedException
	{
		CountDownLatch latch = new CountDownLatch(5);
		for(int i=0;i<5;i++)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run() 
				{
					try
					{
						TimeUnit.SECONDS.sleep(new Random().nextInt(10)+20);
					}
					catch(Exception e)
					{
						
					}
					System.out.println("演员:"+Thread.currentThread().getName()+"来了");
					//门栓做减法,减到0 门栓打开
					latch.countDown();
				}
				
		    },i+"").start();
		}
		
		Thread thread = new Thread()
		{
			@Override
			public void run() 
			{
				try 
				{
					//只有countDown 5次次数继续执行,否则一直阻塞
					latch.await();
					System.out.println(Thread.currentThread().getName()+":可以开始了");
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		};
		thread.setName("制片");
		thread.start();
		
		Thread productor = new Thread()
		{
			@Override
			public void run() 
			{
				try 
				{
					//只等待30 30秒后就不阻塞了
					latch.await(1,TimeUnit.SECONDS);
					if(latch.getCount() == 0)
					{
						System.out.println(Thread.currentThread().getName()+":可以开始了");
					}
					else
					{
						System.out.println(Thread.currentThread().getName()+":还有人没来");
					}
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		};
		productor.setName("投资商");
		productor.start();
		
		
		//可以多处阻塞
		latch.await();
		System.out.println("导演:可以开始了");
	}
}
