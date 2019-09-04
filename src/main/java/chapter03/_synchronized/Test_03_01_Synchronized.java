package chapter03._synchronized;

public class Test_03_01_Synchronized 
{
	private final  Object o = new Object();
	
	public void m1()
	{
		synchronized(o)//锁的是该对象
		{
			
		}
	}
	
	public synchronized void  m2()//锁的是当前对象this
	{
		
	}
	
	public synchronized static void m3() //锁的是Class对象
	{
		
	}
}
