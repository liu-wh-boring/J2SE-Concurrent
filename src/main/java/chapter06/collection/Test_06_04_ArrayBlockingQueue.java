package chapter06.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 有界队列,默认情况下是非公平访问
 */
public class Test_06_04_ArrayBlockingQueue 
{
	private final static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10,true);
	
	@Test
	public void testMethod()
	{
		//方法和LinkedBlockingQueue一致
	}
	
	/**
	 * 公平性和非公平性测试
	 */
	@Test
	public void testfair() throws InterruptedException
	{
		for(int i=0;i<1000;i++)
		{
			new Thread(()->{
				try
				{
					System.out.println(Thread.currentThread()+":"+queue.take());
				}
				catch(Exception e)
				{
					
				}
				
			}) .start();
			//保证线程有序启动
			TimeUnit.MILLISECONDS.sleep(10);
		}
		
		for(;;)
		{
			queue.put(9999999);
			TimeUnit.MILLISECONDS.sleep(1000);
		}
	}
}
