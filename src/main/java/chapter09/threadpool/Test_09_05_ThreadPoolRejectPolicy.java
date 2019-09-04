package chapter09.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略
 *
 */
public class Test_09_05_ThreadPoolRejectPolicy 
{
	static class Task implements Runnable
	{
		@Override
		public void run() 
		{
			while(true);
		}
	}
	
	/**
	 * 自定义策略，永不抛弃
	 *
	 */
	static class MyPolicy implements RejectedExecutionHandler
	{
		final static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
		
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) 
		{
			queue.offer(r);
			try 
			{
				executor.getQueue().put(queue.take());
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
	
	final static ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 1000,TimeUnit.HOURS, new SynchronousQueue<Runnable>(),
			Executors.defaultThreadFactory(),
			new ThreadPoolExecutor.CallerRunsPolicy());
	
	public static void main(String[] args)
	{
	
		pool.execute(new Task());	
		//AbortPolicy 直接抛出异常
		//DiscardPolicy 扔掉不处理
		//DiscardOldestPolicy 队伍头的扔掉，将当前塞入队列中
		//CallerRunsPolicy直接调用run方法
		pool.execute(new Task()); //饱和策略
	}
}
