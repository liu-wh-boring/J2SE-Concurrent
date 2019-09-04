package chapter09.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class Test_09_09_ScheduledThreadPoolExecutor 
{
	private final static ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
	
	static class Task implements Runnable
	{
		@Override
		public void run() 
		{
			System.out.println("我在执行。。。。。");
		}
	}
	
	static class TaskC implements Callable<String>
	{
		@Override
		public String call() throws Exception
		{
			return "hao";
		}
	}
	
	@Test
	public void test()
	{
		//指定时延后调度执行任务
		// public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
		pool.schedule(new Task(), 30, TimeUnit.SECONDS);
		LockSupport.park();
	}
	
	@Test
	public void test1() throws InterruptedException, ExecutionException
	{
		  //指定时延后调度执行任务
		  //  public <V> ScheduledFuture<V> schedule(Callable<V> callable,long delay, TimeUnit unit);
		ScheduledFuture<String> f = pool.schedule(new TaskC(), 30, TimeUnit.SECONDS);
		System.out.println(f.get());
		LockSupport.park();
	}
	
	@Test
	public void test2() throws InterruptedException, ExecutionException
	{
		   //指定时延后开始执行任务，以后每隔period的时长再次执行该任务
		   // public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,TimeUnit unit);
		ScheduledFuture<?> f = pool.scheduleAtFixedRate(new Task(), 30,10, TimeUnit.SECONDS);
		
		while(true)
		{
			System.out.println(f.get());
		}
	}
	
	@Test
	public void test3() throws InterruptedException, ExecutionException
	{
		 //指定时延后开始执行任务，以后任务执行完成后等待delay时长，再次执行任务
	    //public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay,long delay,TimeUnit unit)
		ScheduledFuture<?> f = pool.scheduleWithFixedDelay(new Task(), 30,10, TimeUnit.SECONDS);
		
		while(true)
		{
			System.out.println(f.get());
		}
	}
}
