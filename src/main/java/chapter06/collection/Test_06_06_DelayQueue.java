package chapter06.collection;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 延迟队列,该队列是无界的
 * 用优先级队列实现
 * 注意：compareTo需要实现的是队列的排列,排列要保证剩余时间长的在后面短的在前面
 * 否则最后某些元素在队后等了很长时间都不会取到
 * 注意:不会根据超时时间自动排序的
 */
public class Test_06_06_DelayQueue 
{
	private final static DelayQueue<Person> queue = new DelayQueue<Person>();
	
	static class Person implements Delayed
	{
		private long now;
		private String name;
		long timeOut;
		
		public Person(String name,long timeOut) 
		{
			this.now = System.currentTimeMillis();
			this.name = name;
			this.timeOut = timeOut;
		}

		/**
		 * 排序,放到优先级队列中
		 */
		@Override
		public int compareTo(Delayed o) 
		{
			Person p  = (Person)o;
			return (int)(p.timeOut - this.timeOut)*-1;
		}
		
		/**
		 * 获取剩余时间,<=0 可以获取到队列元素
		 */
		@Override
		public long getDelay(TimeUnit unit) //unit = NANOSECONDS
		{
			return timeOut*1000-(System.currentTimeMillis()-now); //<=0 可以获取到队列元素
		}

		@Override
		public String toString() {
			return new Date()+"=Person [now=" + now + ", name=" + name + ", timeOut=" + timeOut + "]";
		}
		
	}
	
	@Test
	public void testAdd() throws InterruptedException
	{
		queue.put(new Person("A-1",20));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		queue.put(new Person("B-1",3));
		queue.put(new Person("A-2",5));
		queue.put(new Person("B-2",4));
		System.out.println(new Date());
		while(true)
		{
			/**
			 * 从优先级队列中找出队头元素,如果超时了就可以取出
			 */
			System.out.println(queue.take());
		}
	}
}
