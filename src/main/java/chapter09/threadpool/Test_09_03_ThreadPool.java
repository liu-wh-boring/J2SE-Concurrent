package chapter09.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
1.线程池判断核心线程池里的线程是否都在执行任务。
如果不是，则创建一个新的工作线程来执行任务。
如果核心线程池里的线程都在执行任务，则进入下个流程。

2.线程池判断工作队列是否已经满。
如果工作队列没有满，则将新提交的任务存储在这个工作队列里。
如果工作队列满了，则进入下个流程。

3.线程池判断线程池的线程是否都处于工作状态。
如果没有，则创建一个新的工作线程来执行任务。
线程数量不能超过最大线程数

4.如果已经满了，则交给饱和策略来处理这个任务
 *
 */
public class Test_09_03_ThreadPool 
{
	static class Task implements Runnable
	{
		@Override
		public void run() 
		{
			while(true);
		}
	}
	public static void main(String[] args)
	{
		ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 1000,TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(5));
		//测试第一条
		pool.execute(new Task());	pool.execute(new Task());	pool.execute(new Task());//三个任务执行
		System.out.println("活动线程数"+pool.getActiveCount());
		System.out.println("等待队列"+pool.getQueue());
		System.out.println("------------------------------------------------------------");
		
		//测试第二条
		pool.execute(new Task());	pool.execute(new Task());	pool.execute(new Task());pool.execute(new Task());	pool.execute(new Task());//五个任务等待执行
		System.out.println("活动线程数"+pool.getActiveCount());
		System.out.println("等待队列"+pool.getQueue());
		System.out.println("------------------------------------------------------------");
		
		//测试第三条
		pool.execute(new Task());	pool.execute(new Task());//两个任务等待执行
		System.out.println("活动线程数"+pool.getActiveCount());
		System.out.println("等待队列"+pool.getQueue());
		System.out.println("------------------------------------------------------------");
		
		//测试第四条
		pool.execute(new Task()); //饱和策略
	}
}
