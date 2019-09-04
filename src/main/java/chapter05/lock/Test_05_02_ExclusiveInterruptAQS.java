package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test_05_02_ExclusiveInterruptAQS extends AbstractQueuedSynchronizer
{
	private static final long serialVersionUID = -2171456705363293510L;
	
	private final  static Test_05_02_ExclusiveInterruptAQS AQS = new Test_05_02_ExclusiveInterruptAQS();
	
	Test_05_02_ExclusiveInterruptAQS()
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
	 * 普通情况下调试源码
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException
	{
		new Thread(new Task(),"张飞").start();
		new Thread(new Task(),"关羽").start();
		new Thread(new Task(),"赵云").start();
		Thread hz = new Thread(new Task(),"黄忠");
		Thread mc = new Thread(new TaskInterrupt(),"马超");
		
		TimeUnit.SECONDS.sleep(3);
		hz.start();
		mc.start();
		
		//看看两种情况下中断的处理
		TimeUnit.SECONDS.sleep(3);
		hz.interrupt();//普通的acquire不响应中断
		mc.interrupt();//只用acquireInterruptibly响应中断
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
		 static class TaskInterrupt implements Runnable 
		{	
			@Override
			public void run() 
			{
				String name = Thread.currentThread().getName();
				try
				{
					/**
					 * 模板方法，与acquire相同，但是此方法可以响应中断，当前线程未获取到同步状态而进入同步队列中，如果当前线程被中断，此方法会抛出InterruptedException并返回
					 */
					System.out.println(name+":"+"等待获取");
					AQS.acquireInterruptibly(1);;
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
