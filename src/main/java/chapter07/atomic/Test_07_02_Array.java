package chapter07.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.Test;

public class Test_07_02_Array 
{
	
	private final static int ARRAY_LENGTH = 5;
	private final static int THREADS_PER_POS = 3;
	
	/**
	 * 操作的是数组中的数据不是引用
	 * @throws InterruptedException
	 */
	@Test
	public void testArrayAtomicIntegerArray() throws InterruptedException
	{
		AtomicIntegerArray aia = new AtomicIntegerArray(ARRAY_LENGTH);
		
		CountDownLatch latch = new CountDownLatch(THREADS_PER_POS*ARRAY_LENGTH);
		ExecutorService pool = Executors.newFixedThreadPool(THREADS_PER_POS*ARRAY_LENGTH);
		for(int i=0;i<aia.length();i++)
		{
			final int pos = i;
			
			for(int j=0;j<3;j++)
			{
				pool.execute(()->{
					for(int k=0;k<10000000;k++)
					{
						aia.incrementAndGet(pos);
					}
					latch.countDown();
				});
			}
		}
		
		latch.await();
		System.out.println(aia.toString());
	}
	
	@Test
	public void testMethod() 
	{
		@SuppressWarnings("unused")
		AtomicIntegerArray aia = new AtomicIntegerArray(5);
		@SuppressWarnings("unused")
		AtomicLongArray ala = new AtomicLongArray(5);
		@SuppressWarnings("unused")
		AtomicReferenceArray<String> ara = new AtomicReferenceArray<String>(5);
	}
}
