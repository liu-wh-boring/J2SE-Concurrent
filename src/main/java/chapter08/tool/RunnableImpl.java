package chapter08.tool;

import java.util.concurrent.TimeUnit;

public class RunnableImpl implements Runnable 
{
		
	@Override
	public void run()
	{
		String name = Thread.currentThread().getName();
		System.out.println(name+"begin.......");
		try
		{
			TimeUnit.SECONDS.sleep(30);
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			System.out.println(name+"end.......");
		}
	}

}
