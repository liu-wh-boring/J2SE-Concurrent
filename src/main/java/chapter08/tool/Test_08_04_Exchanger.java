package chapter08.tool;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Test_08_04_Exchanger 
{

	/**
	 * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交换。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。这两个线程通过
	   exchange方法交换数据，如果第一个线程先执行exchange()方法，它会一直等待第二个线程也执行exchange方法，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方
	 * @throws InterruptedException 
	 */
	@Test
	public void testExchanger() throws InterruptedException
	{
		Exchanger<String> exchanger = new Exchanger<String>();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		pool.submit(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					
					String to = "我是一条狗";
					System.out.println("dog to cat :"+to);
					String from = exchanger.exchange(to);
					System.out.println("dog from cat :"+from);
					
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		TimeUnit.SECONDS.sleep(10);
		
		pool.submit(new Runnable() 
		{
			@Override
			public void run() 
			{
				try 
				{
					String to = "我是一只猫";
					System.out.println("cat to dog :"+to);
					String from = exchanger.exchange(to);
					System.out.println("cat from dog :"+from);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
