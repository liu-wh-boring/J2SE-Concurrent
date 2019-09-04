package chapter06.collection;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 有界限阻塞队列
 *
 */
public class Test_06_03_LinkedBlockingQueue 
{
	//如果不指定长度,默认是int的最大值
	private final static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(9);
	
	/**
	 * 插入;add();如果对了满了,抛出异常
	 */
	@Test
	public void testAdd()
	{
		for(int i=0;i<10;i++)
		{
			try
			{
				System.out.println("插入----"+i);
				queue.add(i);
			}
			catch(Exception e)
			{
				//java.lang.IllegalStateException: Queue full
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * AddAll()和add一样
	 */
	@Test
	public void testAddAll()
	{
		try
		{
			queue.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,0));
			
		}
		catch(Exception e)
		{
			//java.lang.IllegalStateException: Queue full
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入:offer();如果满了返回false
	 */
	@Test
	public void testOffer()
	{
		for(int i=0;i<10;i++)
		{
			System.out.println("插入----"+i);
			boolean result = queue.offer(i);
			System.out.println("插入结果-------"+result);
		}
	}
	
	/**
	 * 插入:offer(e, timeout, unit);如果满了,超时等待;中断敏感的
	 * @throws InterruptedException 
	 */
	@Test
	public void testOfferTimeOut() throws InterruptedException
	{
		for(int i=0;i<10;i++)
		{
			System.out.println("插入----"+i);
			boolean result = queue.offer(i, 2, TimeUnit.SECONDS);
			System.out.println("插入结果-------"+result);
		}
	}
	
	/**
	 * 插入:put(e);如果满了就一直阻塞;
	 * 只有有阻塞就需要考虑是不是中断敏感的;此处显然是中断敏感的
	 * @throws InterruptedException 
	 */
	@Test
	public void testPut() throws InterruptedException
	{
		for(int i=0;i<10;i++)
		{
			System.out.println("插入----"+i);
			queue.put(i);
		}
	}
	
	/**
	 * 测试是否允许插入空置
	 * 测试结果不允许插入null
	 */
	@Test
	public void testInsertNull()
	{
		Integer i = null;
		queue.add(i);
	}
	
	/**
	 * 获取并且移除
	 * 如果没有元素,直接抛出异常
	 * java.util.NoSuchElementException
	 */
	@Test
	public void testRemove()
	{
		try
		{
			@SuppressWarnings("unused")
			Integer i = queue.remove();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取并且移除:poll();如果没有返回null
	 */
	@Test
	public void testPoll()
	{
		queue.add(1);queue.add(2);queue.add(3);
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}
	
	/**
	 * 获取并且移除:poll(timeout,unit);如果没有则等待,超时则返回null
	 */
	@Test
	public void testPollTimeOut() throws InterruptedException
	{
		queue.add(1);queue.add(2);queue.add(3);
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll(10,TimeUnit.SECONDS));
	}
	
	/**
	 * 获取并且移除元素,take();如果没有则一直阻塞
	 * @throws InterruptedException
	 */
	@Test
	public void testTake()throws InterruptedException
	{
		queue.add(1);queue.add(2);queue.add(3);
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
	}
	
	/**
	 * 获取并且不移除头端元素,如果没有则抛出异常
	 * java.util.NoSuchElementException
	 */
	@Test
	public void testElement()
	{
		queue.element();
	}
	
	/**
	 * 获取并且不移除头端元素,如果没有则返回null
	 */
	@Test
	public void testPeek()
	{
		queue.add(1);
		System.out.println(queue.peek());
		System.out.println(queue.peek());
		System.out.println(queue.peek());
		
		queue.clear();
		System.out.println(queue.peek());
	}
	
}
