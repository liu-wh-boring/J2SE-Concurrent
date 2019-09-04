package chapter04.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test_04_11_ThreadPoolAndThreadLocal 
{
	static class Task implements Runnable
	{

		private final static ThreadLocal<String> LOCAL = new ThreadLocal<String>();
		
		@Override
		public void run() 
		{
			String str = LOCAL.get();
			if(str==null)
			{
				LOCAL.set("996997998999");
			}
			else
			{
				System.out.println(Thread.currentThread()+":"+str);
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		ExecutorService pool = Executors.newFixedThreadPool(1);
		for(int i=0;i<10;i++)
		{
			pool.submit(new Task());
		}
		pool.shutdown();
		while(true)
		{
			if(pool.isTerminated())
			{
				System.out.println("game over");
				break;
			}
		}
	}
}
