package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 共享锁
 * 双胞胎锁，只有两个资源
 *
 */
public class Test_05_08_TwinsLock implements Lock
{
		static class TwinsLock extends  AbstractQueuedSynchronizer
		{
				private static final long serialVersionUID = 1060203147514512180L;

				private TwinsLock()
				{
					super.setState(2);
				}
				
				@Override
				protected int tryAcquireShared(int permits) 
				{
					for(;;)
					{
						int c = super.getState();
						int left = c-1;
						if(left < 0 || super.compareAndSetState(c, left))
						{
							return left;
						}
					}
				}

				@Override
				protected boolean tryReleaseShared(int permits) 
				{
						for(;;)
						{
							int c = super.getState();
							if(super.compareAndSetState(c, c+1))
							{
								return true;
							}
						}
				}
		}
		
		private final  TwinsLock twins = new TwinsLock();

		@Override
		public void lock() 
		{
			twins.acquireShared(1);
		}

		@Override
		public void lockInterruptibly() throws InterruptedException 
		{
			twins.acquireInterruptibly(1);
		}

		@Override
		public boolean tryLock() {
			return false;
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			return false;
		}

		@Override
		public void unlock() {
			twins.releaseShared(1);
		}

		@Override
		public Condition newCondition() 
		{
			return null;
		}
		private final static Lock lock = new Test_05_08_TwinsLock();
		public static void main(String[] args)
		{
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
