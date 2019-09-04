package chapter06.collection;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.Test;

/**
 * PriortyBlockingQueue队列是无界队列
 * 优先级队列
 * 可以两种方式支持优先级
 * 1)直接定义Comparator
 * 2)直接比较的类继承compareable
 * @author liu13
 *
 */
public class Test_06_05_PriorityBlockingQueue
{
	/**
	 * 定义比较器
	 */
	private final static PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<Integer>(50, new Comparator<Integer>(){
		
				@Override
				public int compare(Integer o1, Integer o2) 
				{

					return o1.compareTo(o2);
				}
			});
	
	@Test
	public void testAdd()
	{
		queue.put(9);		queue.put(9);		queue.put(9);		queue.put(9);		queue.put(9);
 		queue.put(8);		queue.put(8);		queue.put(8);		queue.put(8);		queue.put(8);
 		queue.put(7);		queue.put(7);		queue.put(7);		queue.put(7);		queue.put(7);
 		queue.put(6);		queue.put(6);		queue.put(6);		queue.put(6);		queue.put(6);
 		queue.put(5);		queue.put(5);		queue.put(5);		queue.put(5);		queue.put(5);
 		queue.put(4);		queue.put(4);		queue.put(4);		queue.put(4);		queue.put(4);
 		queue.put(3);		queue.put(3);		queue.put(3);		queue.put(3);		queue.put(3);
 		queue.put(2);		queue.put(2);		queue.put(2);		queue.put(2);		queue.put(2);
 		queue.put(1);		queue.put(1);		queue.put(1);		queue.put(1);		queue.put(1);
		
		new Thread(()->{
			try 
			{
				while(true)
				System.out.println(queue.take());
			} catch (InterruptedException e) 
			{
				
			}
		}).start();
	}
	
	/**
	 * 比较类继承Comparable接口
	 *
	 */
	static class NO implements Comparable<NO>
	{
		private int num;
		
		private NO(int num) 
		{
			this.num = num;
		}

		@Override
		public int compareTo(NO o) 
		{

			return Integer.compare(this.num, o.num)*-1;
		}
		
		public String toString()
		{
			return num+"";
		}
	}
	
	private final static PriorityBlockingQueue<NO> queue2 = new PriorityBlockingQueue<NO>();
	
	@Test
	public void testAdd2()
	{
		queue2.put(new NO(9));		queue2.put(new NO(9));		queue2.put(new NO(9));		queue2.put(new NO(9));		queue2.put(new NO(9));
 		queue2.put(new NO(8));		queue2.put(new NO(8));		queue2.put(new NO(8));		queue2.put(new NO(8));		queue2.put(new NO(8));
 		queue2.put(new NO(7));		queue2.put(new NO(7));		queue2.put(new NO(7));		queue2.put(new NO(7));		queue2.put(new NO(7));
 		queue2.put(new NO(6));		queue2.put(new NO(6));		queue2.put(new NO(6));		queue2.put(new NO(6));		queue2.put(new NO(6));
 		queue2.put(new NO(5));		queue2.put(new NO(5));		queue2.put(new NO(5));		queue2.put(new NO(5));		queue2.put(new NO(5));
 		queue2.put(new NO(4));		queue2.put(new NO(4));		queue2.put(new NO(4));		queue2.put(new NO(4));		queue2.put(new NO(4));
 		queue2.put(new NO(3));		queue2.put(new NO(3));		queue2.put(new NO(3));		queue2.put(new NO(3));		queue2.put(new NO(3));
 		queue2.put(new NO(2));		queue2.put(new NO(2));		queue2.put(new NO(2));		queue2.put(new NO(2));		queue2.put(new NO(2));
 		queue2.put(new NO(1));		queue2.put(new NO(1));		queue2.put(new NO(1));		queue2.put(new NO(1));		queue2.put(new NO(1));	
		
		new Thread(()->{
			try 
			{
				while(true)
				System.out.println(queue2.take());
			} catch (InterruptedException e) 
			{
				
			}
		}).start();
	}
	 
}
