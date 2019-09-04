package chapter04.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *ThreadLocal底层是通过ThreadLocalMap实现的，就是一个key是当前线程的MAP
 */
public class Test_04_09_ThreadLocal {

	static class Task implements Runnable
	{
		int k=0;
		
		private final static ThreadLocal<Long>  begin = new ThreadLocal<Long>();
		private final static ThreadLocal<Long>  end = new ThreadLocal<Long>();
		
		@Override
		public void run() {
			
			begin.set(System.currentTimeMillis());
			for(int i=0;i<Integer.MAX_VALUE;i++)
			{
				k++;
			}
			end.set(System.currentTimeMillis());
			
			System.out.println("begin="+begin.get()+" end="+end.get());
		}
	}
	
	public static void main(String[] args) 
	{
		List<Thread> list = new ArrayList<Thread>();
		for(int i=0;i<10;i++)
		{
			list.add(new Thread(new Task()));
		}
		
		for(Thread i : list)
		{
			i.start();
		}
	}

}
