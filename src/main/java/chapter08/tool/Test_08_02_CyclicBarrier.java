package chapter08.tool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * CyclicBarrier 循环屏障
 * 可以循环使用的屏障
 * 让一个线程达到屏障(同步点)时阻塞,直到最后一个屏障到达时才会开门
 *
 */
public class Test_08_02_CyclicBarrier 
{
	@Test
	public void testCyclicBarrier() throws InterruptedException, BrokenBarrierException
	{
		//定义屏障数
		int number = 5;
		CyclicBarrier barrier = new CyclicBarrier(number);
		
		for(int i=1;i<number;i++)
		{
			new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					String name = Thread.currentThread().getName();
					try
					{
						
						System.out.println(name+"->begin.......");
						//此处阻塞
						barrier.await();
					}
					catch(Exception e)
					{
						
					}
					finally
					{
						System.out.println(name+"->end.......");
					}
					
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(10);
		
		//barrier.getParties() : Returns the number of parties required to trip this barrier.
		System.out.println("屏障数="+barrier.getParties()); //5
		
		//eturns the number of parties currently waiting at the barrier.
		System.out.println("抵达屏障数="+barrier.getNumberWaiting()); //4 所以永远会阻塞
	}
	
	/**
	 * 时间敏感,某一个超时,所以的离开到达屏障
	 * @throws InterruptedException
	 * @throws BrokenBarrierException
	 */
	@Test
	public void testCyclicBarrierTimeOut() throws InterruptedException, BrokenBarrierException
	{
		//定义屏障数
		int number = 5;
		CyclicBarrier barrier = new CyclicBarrier(number);
		
		for(int i=1;i<number-1;i++)
		{
			new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					String name = Thread.currentThread().getName();
					try
					{
						
						System.out.println(name+"->begin.......");
						//此处阻塞,等待20秒,还没有到达屏障,直接抛出异常
						barrier.await();
					}
					catch(Exception e)
					{
						
					}
					finally
					{
						System.out.println(name+"->end.......");
					}
					
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(10);
		
		//barrier.getParties() : Returns the number of parties required to trip this barrier.
		System.out.println("屏障数="+barrier.getParties()); //5
		
		//eturns the number of parties currently waiting at the barrier.
		System.out.println("抵达屏障数="+barrier.getNumberWaiting()); //4 所以永远会阻塞
		
		String name = Thread.currentThread().getName();
		try
		{
			System.out.println(name+"->begin.......");
			//抛出异常,所有的线程都离开到达屏障
			barrier.await(20,TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			System.out.println(name+"->end.......");
		}
	}
	
	/**
	 * 中断敏感
	 * @throws InterruptedException
	 * @throws BrokenBarrierException
	 */
	@Test
	public void testCyclicBarrierInterrupt()
	{
		//定义屏障数
		int number = 5;
		CyclicBarrier barrier = new CyclicBarrier(number);
		
		for(int i=1;i<number-1;i++)
		{
			new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					String name = Thread.currentThread().getName();
					try
					{
						System.out.println(name+"->begin.......");
						//此处阻塞,等待20秒,还没有到达屏障,直接抛出异常
						barrier.await();
					}
					catch(Exception e)
					{
						
					}
					finally
					{
						System.out.println(name+"->end.......");
					}
					
				}
			}).start();
		}
		
		try 
		{
			TimeUnit.SECONDS.sleep(10);
		} 
		catch (InterruptedException e1) 
		{
			e1.printStackTrace();
		}
		
		//barrier.getParties() : Returns the number of parties required to trip this barrier.
		System.out.println("屏障数="+barrier.getParties()); 
		
		//eturns the number of parties currently waiting at the barrier.
		System.out.println("抵达屏障数="+barrier.getNumberWaiting()); 
		
		
		final Thread current = Thread.currentThread();
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					TimeUnit.SECONDS.sleep(5);
					System.out.println("准备中断主线程");
					current.interrupt();//中断后所有的屏障释放
				}
				catch(Exception e)
				{
					
				}
			}
			
		}.start();
		
		
		String name = current.getName();
		try
		{
			System.out.println(name+"->begin.......");
			System.out.println("是否中断了-->"+barrier.isBroken());
			barrier.await();//前面八个,这个一个 理论上永远都不会到达屏障
			
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			System.out.println("是否中断了-->"+barrier.isBroken());
			System.out.println(name+"->end.......");
		}
	}
	
	
	public void testBarrierAction()
	{
		int number = 5;
		CyclicBarrier barrier = new CyclicBarrier(number);
		for(int i=1;i<number;i++)
		{
			new Thread(new Runnable() 
			{
				@Override
				public void run() 
				{
					String name = Thread.currentThread().getName();
					try
					{
						barrier.await();
						System.out.println(name+"->begin.......");
						
					}
					catch(Exception e)
					{
						
					}
					finally
					{
						System.out.println(name+"->end.......");
					}
					
				}
			}).start();
		}
	}
	
	@Test
	public void testCyclicBarrierReset()
	{
		int number = 5;
		CyclicBarrier barrier = new CyclicBarrier(number,new Runnable() 
		{
			@Override
			public void run() 
			{
				System.out.println("结束了！！！！！！！！！！！！！！！！");
			}
			
		});
		barrier.reset();//中断，唤醒所有的等待线程,让障碍器可以重用
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<number;j++)
			{
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							barrier.await();
						}
						catch(Exception e)
						{
							
						}
					}
					
				}.start();
			}
		}
		
	}
}
