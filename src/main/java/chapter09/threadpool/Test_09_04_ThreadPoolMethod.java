package chapter09.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class Test_09_04_ThreadPoolMethod 
{
	static class Task implements Runnable
	{
		@Override
		public void run() 
		{
			//while(true);
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
	
	final static ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 1000,TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(5));
	
	/**
	 * 任务的提交方式
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Test
	public void test() throws InterruptedException, ExecutionException
	{
		pool.execute(new Task()); //直接提交Runnable
		
		Future<?> f= pool.submit(new Task());
		System.out.println(f.get());//返回为null,告知任务结束
		
		Future<String> f2= pool.submit(new Task(),"黄瓜视频");
		System.out.println(f2.get());//返回为黄瓜视频
		
		Future<String> f3= pool.submit(new TaskC());
		System.out.println(f3.get());//返回为结果值
		
		LockSupport.park();
	}
	
	/**
	 * 查询运行信息
	 */
	public void test1()
	{
		System.out.println("活动线程数"+pool.getActiveCount());
		System.out.println("完成线程数"+pool.getCompletedTaskCount());
		System.out.println("等待队列数"+pool.getQueue());
	}
	
	/**
	 * 控制
	 */
	public void test2()
	{
		pool.shutdown();//关闭线程池
		pool.shutdownNow();//立刻关闭线程池
		
		pool.isShutdown();//是否关闭只要调用了这两个关闭方法中的任意一个，isShutdown方法就会返回true
		pool.isTerminated();//当所有的任务都已关闭后，才表示线程池关闭成功，这时调用isTerminaed方法会返回true
	}
}
