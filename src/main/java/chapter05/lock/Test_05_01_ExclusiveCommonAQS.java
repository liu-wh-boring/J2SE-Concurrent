package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class Test_05_01_ExclusiveCommonAQS extends AbstractQueuedSynchronizer
{
	private static final long serialVersionUID = -2171456705363293510L;
	
	private final  static Test_05_01_ExclusiveCommonAQS AQS = new Test_05_01_ExclusiveCommonAQS();
	
	Test_05_01_ExclusiveCommonAQS()
	{
		super.setState(1);
	}
	/**
	 * 钩子方法，独占式获取同步状态，AQS没有具体实现，具体实现都在子类中，
	 * 实现此方法需要查询当前同步状态并判断同步状态是否符合预期，然后再CAS设置同步状态
	 */
	@Override
	public boolean tryAcquire(int arg) 
	{
			if(super.compareAndSetState(1, 0))
			{
				super.setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
	}
	
	/**
	 * 钩子方法，独占式释放同步状态，AQS没有具体实现，具体实现都在子类中，
	 * 等待获取同步状态的线程将有机会获取同步状态
	 */
	@Override
	public boolean tryRelease(int arg) 
	{
		for(;;)
		{
			return this.compareAndSetState(0, 1);
		}
	}

	@Override
	public boolean isHeldExclusively() 
	{
		return Thread.currentThread() == super.getExclusiveOwnerThread();
	}
	
	public Condition condition()
	{
		return new ConditionObject();
	}
	/**
	 * 普通情况下调试源码
	 */
	public static void main(String[] args)
	{
		new Thread(new Task(),"张飞").start();
		new Thread(new Task(),"关羽").start();
		new Thread(new Task(),"赵云").start();
		new Thread(new Task(),"黄忠").start();
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
			/**
			 * 模板方法，独占式获取同步状态，如果当前线程获取同步状态成功，则由该方法返回，否则会进入同步队列等待，此方法会调用子类重写的tryAcquire方法
			 */
			AQS.acquire(1);
			System.out.println(name+":"+"已经获取锁");
			try
			{
				TimeUnit.SECONDS.sleep(5);
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
	
}
