package chapter03._synchronized;

import java.util.concurrent.TimeUnit;

public class Test_03_06_DeadLock
{

	private final static Object LOCK_A =  new Object();
	private final static Object LOCK_B =  new Object();
	
	public static void main(String[] args) 
	{
		new Thread(()->{
			synchronized(LOCK_A)
			{
				try 
				{
					TimeUnit.SECONDS.sleep(2);
				} 
				catch (InterruptedException e) 
				{
					
				}
				
				synchronized(LOCK_B)
				{
					
				}
			}
		},"赵钱孙李") .start(); 
		
		new Thread(()->{
			synchronized(LOCK_B)
			{
				try 
				{
					TimeUnit.SECONDS.sleep(2);
				} 
				catch (InterruptedException e) 
				{
					
				}
				
				synchronized(LOCK_A)
				{
					
				}
			}
		},"周吴郑王") .start(); 
	}
}
