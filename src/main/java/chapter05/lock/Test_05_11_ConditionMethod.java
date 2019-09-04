package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class Test_05_11_ConditionMethod 
{
	private final static Test_05_01_ExclusiveCommonAQS lock = new Test_05_01_ExclusiveCommonAQS ();
	private final static Condition condition = lock.condition();
	
	public static void  main (String[] args) throws InterruptedException
	{
		new Thread(()->{
			lock.acquire(1);
			try
			{
				TimeUnit.SECONDS.sleep(15);
			}
			catch(Exception e)
			{
				
			}
			System.out.println("-------------------初始阶段---------------------");
			System.out.println("同步队列"+lock.getExclusiveQueuedThreads());
			System.out.println("条件队列"+lock.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition));
			System.out.println("-------------------初始阶段---------------------");
			System.out.println();
			
			lock.release(1);
			
		},"张飞") .start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(()->{
			lock.acquire(1);
			try 
			{
				condition.await();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		},"关羽") .start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(()->{
			lock.acquire(1);
			
			System.out.println("-------------------张飞释放锁，关羽执行等待,马超获取锁---------------------");
			System.out.println("同步队列"+lock.getExclusiveQueuedThreads());
			System.out.println("条件队列"+lock.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition));
			System.out.println("-------------------张飞释放锁，关羽执行等待，马超获取锁---------------------");
			System.out.println();
			
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		},"马超") .start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(()->{
			lock.acquire(1);
		},"赵云") .start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(()->{
			lock.acquire(1);
		},"黄忠") .start();
		TimeUnit.SECONDS.sleep(2);
	}
	
}
