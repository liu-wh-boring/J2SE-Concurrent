package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test_05_03_ExclusiveTimeoutAQS extends AbstractQueuedSynchronizer
{
	private static final long serialVersionUID = -2171456705363293510L;
	
	private final  static Test_05_03_ExclusiveTimeoutAQS AQS = new Test_05_03_ExclusiveTimeoutAQS();
	
	Test_05_03_ExclusiveTimeoutAQS()
	{
		super.setState(1);
	}
	/**
	 * 钩子方法，独占式获取同步状态，AQS没有具体实现，具体实现都在子类中，
	 * 实现此方法需要查询当前同步状态并判断同步状态是否符合预期，然后再CAS设置同步状态
	 */
	@Override
	protected boolean tryAcquire(int arg) 
	{
			if(super.compareAndSetState(1, 0))
			{
				return true;
			}
			return false;
	}
	
	/**
	 * 钩子方法，独占式释放同步状态，AQS没有具体实现，具体实现都在子类中，
	 * 等待获取同步状态的线程将有机会获取同步状态
	 */
	@Override
	protected boolean tryRelease(int arg) 
	{
		for(;;)
		{
			return this.compareAndSetState(0, 1);
		}
	}

	/**
	 * 查看时间敏感性
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException
	{
		new Thread(new Task(),"张飞").start();
		new Thread(new Task(),"关羽").start();
		new Thread(new TaskTimeout(),"赵云").start();
		new Thread(new Task(),"黄忠").start();;
		new Thread(new Task(),"马超").start();
	}
	
	/**
	 * 普通情况下的任务获取锁的方式
	 */
	 static class Task implements Runnable 
	{	
		@Override
		public void run() 
		{
			String name = Thread.currentThread().getName();
			try
			{
				/**
				 * 模板方法，独占式获取同步状态，如果当前线程获取同步状态成功，则由该方法返回，否则会进入同步队列等待，此方法会调用子类重写的tryAcquire方法
				 */
				System.out.println(name+":"+"等待获取");
				AQS.acquire(1);
				System.out.println(name+":"+"已经获取锁");
				TimeUnit.SECONDS.sleep(5000);
			}
			catch(Exception e)
			{
			}
			/**
			 * 模板方法，独占式的释放同步状态，该方法会在释放同步状态后，将同步队列中的第一个节点包含的线程唤醒
			 */
			AQS.release(1);
			System.out.println(name+":"+"已经释放锁");
		}
	}
	 
	 	/**
		 * 可响应中断
		 */
		 static class TaskTimeout implements Runnable 
		{	
			@Override
			public void run() 
			{
				String name = Thread.currentThread().getName();
				try
				{
					/**
					 * 模板方法，在acquireInterruptibly基础上增加了超时限制，如果当前线程在超时时间内没有获取到同步状态，
					 * 则会返回false,如果获取到了则会返回true
					 */
					System.out.println(name+":"+"等待获取");
					AQS.tryAcquireNanos(1, TimeUnit.SECONDS.toNanos(30));
					System.out.println(name+":"+"已经获取锁");
					TimeUnit.SECONDS.sleep(5);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				/**
				 * 模板方法，独占式的释放同步状态，该方法会在释放同步状态后，将同步队列中的第一个节点包含的线程唤醒
				 */
				AQS.release(1);
				System.out.println(name+":"+"已经释放锁");
			}
		}
	
}
