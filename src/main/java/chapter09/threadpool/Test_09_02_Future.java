package chapter09.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Test_09_02_Future 
{
	static class Task implements Callable<String>
	{

		@Override
		public String call() throws Exception 
		{
			TimeUnit.SECONDS.sleep(5);
			return "可以开始了";
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		FutureTask<String> future = new FutureTask<String>(new Task());
//		future.cancel(false);
		new Thread(future,"张飞").start();
		
		new Thread(()->{
			try 
			{
				future.get();
			} catch (InterruptedException | ExecutionException e) 
			{
				e.printStackTrace();
			}
		},"关羽") .start();
		
		new Thread(()->{
			try 
			{
				future.get();
			} catch (InterruptedException | ExecutionException e) 
			{
				e.printStackTrace();
			}
		},"赵云") .start();
		
		new Thread(()->{
			try 
			{
				future.get();
			} catch (InterruptedException | ExecutionException e) 
			{
				e.printStackTrace();
			}
		},"马超") .start();
	}
}
