package chapter05.lock;

import java.util.concurrent.TimeUnit;

public class Test_05_04_SharedCommonAQS  extends java.util.concurrent.locks.AbstractQueuedSynchronizer
{
	private static final long serialVersionUID = -5872995014263796836L;

	private final static Test_05_04_SharedCommonAQS AQS = new Test_05_04_SharedCommonAQS();
	
	Test_05_04_SharedCommonAQS()
	{
		super.setState(10);
	}
	
	/**
	 * 钩子方法，共享式获取同步状态，AQS没有具体实现，具体实现都在子类中，返回大于等于0的值表示获取成功，反之失败
	 */
	@Override
	protected int tryAcquireShared(int permits) 
	{
		int state = super.getState();
		int left = state -permits;
		if(left >= 0)
		{
			if(super.compareAndSetState(state,left))
			{
				return left;
			}
		}
		return -1;
	}

	/**
	 * 钩子方法，共享式释放同步状态，AQS没有具体实现，具体实现都在子类中
	 */
	@Override
	protected boolean tryReleaseShared(int permits) 
	{
		for(;;)
		{
			int state = super.getState();
			if(super.compareAndSetState(state, state+permits))
			{
				return true;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new Thread(new Task(60),"张飞").start();
		
		Thread.sleep(3000);
		
		new Thread(new Task(),"关羽").start();
		new Thread(new Task(),"赵云").start();
		new Thread(new Task(),"黄忠").start();
		new Thread(new Task(),"马超").start();
		new Thread(new Task(),"魏延").start();
	}
	
	static class Task implements Runnable
	{
		private Integer time;
		
		Task()
		{
			this(50000);
		}
		
		Task(Integer time)
		{
			 this.time = time;
		}
		
		
		@Override
		public void run() 
		{
			String name = Thread.currentThread().getName();
			try
			{
				/**
				 * 模板方法，共享式的获取同步状态，如果当前系统未获取到同步状态，将会进入同步队列等待，与acquire的主要区别在于同一时刻可以有多个线程获取到同步状态
				 */
				System.out.println(name+":"+"等待获取");
				AQS.acquireShared(6);
				System.out.println(name+":"+"已经获取锁");
				TimeUnit.SECONDS.sleep(time);
			}
			catch(Exception e)
			{
			}
			/**
			 * 模板方法，共享式的释放同步状态
			 */
			AQS.releaseShared(5);
			System.out.println(name+":"+"已经释放锁");
		}
		
	}

}
