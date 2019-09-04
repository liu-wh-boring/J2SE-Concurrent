package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 可重入锁
 * @author thirteenliu
 *
 */
public class Test_05_06_Reentrant implements Lock
{
	static class Reentrant extends AbstractQueuedSynchronizer
	{
		private static final long serialVersionUID = 5446793570707127627L;

		public Reentrant()
		{
			super.setState(1); //初始情况下,有一个权限
		}
		
		@Override
		protected boolean tryAcquire(int arg) 
		{
			if(Thread.currentThread() == super.getExclusiveOwnerThread())//如果是当前线程，可冲入
			{
				return true;
			}
			
			if(super.compareAndSetState(1, 0))//资源还在,可进入
			{
				super.setExclusiveOwnerThread(Thread.currentThread());
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
			super.setExclusiveOwnerThread(null);
			super.setState(1);//释放锁
			return true;
		}
	}

	private final Reentrant reentrant = new Reentrant();
	
	@Override
	public void lock() 
	{
		reentrant.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException 
	{
		reentrant.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() 
	{
		return reentrant.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException 
	{
		return reentrant.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() 
	{
		reentrant.release(1);
	}

	@Override
	public Condition newCondition() 
	{
		return null;
	}
	
	//测试一下重入锁的功能性
	public static  void main(String[] args)
	{
		Lock lock = new Test_05_06_Reentrant();
		
		String name = Thread.currentThread().getName();
		lock.lock();System.out.println(name+":"+"获取🔒");
		lock.lock();System.out.println(name+":"+"获取🔒");
		lock.lock();System.out.println(name+":"+"获取🔒");
		lock.lock();System.out.println(name+":"+"获取🔒");
		System.out.println(name+":"+"释放🔒");
		System.out.println("--------------------------------");
		lock.unlock();
	}
}
