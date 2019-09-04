package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
  * 前面写的都是不公平锁
  * 公平锁
  * 独占锁
  * 重入锁
 */
public class Test_05_07_Fair implements Lock
{
	static class Fair extends AbstractQueuedSynchronizer
	{
		private static final long serialVersionUID = 5446793570707127627L;

		public Fair()
		{
			super.setState(0); //换一种设定，state表是索取锁的次数
		}
		
		@Override
		protected boolean tryAcquire(int permits) 
		{
			int state = super.getState();
			Thread current = Thread.currentThread();
			if(	!super.hasQueuedPredecessors()) //公平
			{
				if(state == 0 )
				{
					if(super.compareAndSetState(state,1))
					{
						super.setExclusiveOwnerThread(current);
						return true;
					}
				}
			}
			else if(current == super.getExclusiveOwnerThread())
			{
				for(;;)
				{
					int c = super.getState();
					super.compareAndSetState(c,c+1);
					return true;
				}
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) 
		{
			if(!super.compareAndSetState(1, 0))
			{
				System.out.println("逻辑错误");
			}
			super.setState(0);//释放锁
			super.setExclusiveOwnerThread(null);
			return true;
		}
	}

	private final Fair fair = new Fair();
	
	@Override
	public void lock() 
	{
		fair.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException 
	{
		fair.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() 
	{
		return fair.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException 
	{
		return fair.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() 
	{
		fair.release(1);
	}

	@Override
	public Condition newCondition() 
	{
		return null;
	}
	
	//测试一下独占锁的功能性
	public static  void main(String[] args)
	{
		Lock lock = new Test_05_07_Fair();
		for(int i=0;i<10;i++)
		{
			final int b = i;
			new Thread(()->{
				try
				{
					String name = Thread.currentThread().getName();
			
					lock.lock();
					System.out.println(name+":"+"获取🔒");
					TimeUnit.SECONDS.sleep(5);
					System.out.println(name+":"+"释放🔒");
					System.out.println("--------------------------------");
					lock.unlock();
				}
				catch(Exception e)
				{
				}
			},"thread-"+b) .start();
		}
	}

}
