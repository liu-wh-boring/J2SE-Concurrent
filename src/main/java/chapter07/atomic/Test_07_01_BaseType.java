package chapter07.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class Test_07_01_BaseType 
{
	@Test
	public void testAtomicInteger() throws InterruptedException 
	{
		AtomicInteger atomic = new AtomicInteger();
		CountDownLatch latch = new CountDownLatch(2);
		
		//验证原子类的操作
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.execute(()->{
			for(int i=0;i<10000000;i++)
			{
				atomic.incrementAndGet();
			}
			latch.countDown();
		});
		
		pool.execute(()->{
			for(int i=0;i<10000000;i++)
			{
				atomic.incrementAndGet();
			}
			latch.countDown();
		});
		
		latch.await();
		System.out.println("最后的结果"+atomic.get());
	}
	
	@Test
	public void testMethod()
	{
		
		@SuppressWarnings("unused")
		AtomicLong al = new AtomicLong();
		@SuppressWarnings("unused")
		AtomicBoolean ab = new AtomicBoolean();
		
		//如果满了怎么ban
		System.out.println("------------------------------------");
		AtomicInteger ai = new AtomicInteger();
		ai.set(Integer.MAX_VALUE);
		System.out.println(ai.incrementAndGet());//和int满了一个概念
		System.out.println(Integer.MAX_VALUE+1);
		
	}
}
