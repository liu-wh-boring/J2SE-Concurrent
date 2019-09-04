package chapter06.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import org.junit.Test;

public class Test_06_01_ConcurrentHashMap 
{
	
	/**
	 * HashMap不安全,容易形成环链
	 */
	@Test
	public void testHashMap()
	{
		final  Map<String,Integer> hashMap = new HashMap<String,Integer>();
	    final  Semaphore semophore = new Semaphore(3);
		
		new Thread(()->{
			for(int i=0;i<100000;i++)
			{
				hashMap.put("i"+i,i);
			}
			semophore.release();
		}).start();
		
		new Thread(()->{
			for(int j=0;j<100000;j++)
			{
				hashMap.put("j"+j,j);
			}
			semophore.release();
		}).start();
		
		new Thread(()->{
			for(int k=0;k<100000;k++)
			{
				hashMap.put("k"+k,k);
			}
			semophore.release();
		}).start();
		
		semophore.acquireUninterruptibly(3);
		System.out.println(hashMap.size());
	}
	
	/**
	 * 性能差
	 * @throws BrokenBarrierException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testHashTable() throws InterruptedException, BrokenBarrierException
	{
		final Map<String,Integer> hashtable = new Hashtable<String,Integer>();
	    final CyclicBarrier barrier = new CyclicBarrier(4);
	    long now = System.currentTimeMillis();
		
		new Thread(()->{
			for(int i=0;i<1000000;i++)
			{
				hashtable.put("i"+i,i);
			}
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();
		
		new Thread(()->{
			for(int j=0;j<1000000;j++)
			{
				hashtable.put("j"+j,j);
			}
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();
		
		new Thread(()->{
			for(int k=0;k<1000000;k++)
			{
				hashtable.put("k"+k,k);
			}
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();
		barrier.await();
		System.out.println("hashtable.size="+hashtable.size());
		System.out.println("hashtable.time="+(System.currentTimeMillis()-now));
	}
	
	/**
	 * 测试ConcurrentHashMap的性能
	 * @throws InterruptedException 
	 */
	@Test
	public void testConcurrentHashMap() throws InterruptedException
	{
		final ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<String,Integer>();
		CountDownLatch latch = new CountDownLatch(3);
		
		long now = System.currentTimeMillis();
		
		new Thread(()->{
			for(int i=0;i<1000000;i++)
			{
				map.put("i"+i,i);
			}
			latch.countDown();
		}).start();
		
		new Thread(()->{
			for(int j=0;j<1000000;j++)
			{
				map.put("j"+j,j);
			}
			latch.countDown();
		}).start();
		
		new Thread(()->{
			for(int k=0;k<1000000;k++)
			{
				map.put("k"+k,k);
			}
			latch.countDown();
		}).start();
		
		latch.await();
		System.out.println("hashtable.size="+map.size());
		System.out.println("hashtable.time="+(System.currentTimeMillis()-now));
	}
	
	public void testMethod()
	{
		ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<String,Integer>();
		
		String id = UUID.randomUUID().toString();
		//put
		map.put(id, 1);
		
		//get
		map.get(id);
		
		//return the previous value associated with the specified key,r {@code null} if there was no mapping for the key
		map.putIfAbsent(id, 2);
	}
}
