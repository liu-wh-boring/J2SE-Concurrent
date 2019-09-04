package chapter10.Patten;

import java.util.LinkedList;

public class Test_10_05_PCPatten 
{
	/**
	 *公共资源
	 */
	static class Resource
	{
		//容器
		private final static LinkedList<String> list = new LinkedList<String>();
		
		//长度
		private final static int MAX_SIZE = 10;
		
		//当前资源
		private static int num;
		
		static synchronized void put(String product)
		{
			if(num<MAX_SIZE)
			{
				list.add(product);
				//
			}
		}
	}
	
	static class Producer implements Runnable
	{

		@Override
		public void run() 
		{
			
		}
		
	}
}
