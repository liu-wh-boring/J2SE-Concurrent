package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁
 * @author thirteenliu
 *
 */
public class Test_05_05_Mutex implements Lock
{
	static class Mutex extends AbstractQueuedSynchronizer
	{
		private static final long serialVersionUID = 5446793570707127627L;

		public Mutex()
		{
			super.setState(1); //初始情况下,有一个权限
		}
		
		@Override
		protected boolean tryAcquire(int arg) 
		{
			if(super.compareAndSetState(1, 0))//资源还在,不可重入
			{
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) 
		{
			if(!super.compareAndSetState(0, 1))
			{
				System.out.println("逻辑错误");
			}
			super.setState(1);//释放锁
			return true;
		}
	}

	private final Mutex mutex = new Mutex();
	
	@Override
	public void lock() 
	{
		mutex.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException 
	{
		mutex.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() 
	{
		return mutex.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException 
	{
		return mutex.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() 
	{
		mutex.release(1);
	}

	@Override
	public Condition newCondition() 
	{
		return null;
	}
	
	//测试一下独占锁的功能性
	public static  void main(String[] args)
	{
		Lock lock = new Test_05_05_Mutex();
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
