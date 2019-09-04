package chapter05.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test_05_10_Condition 
{
	private final static ReentrantLock lock = new ReentrantLock();
	private final static Condition condition = lock.newCondition();
	
	/**
CANCELLED，值为1，在同步队列中等待的线程等待超时或者被中断，需要从同步队列中取消等待，节点进入该状态后将不会变化；
SIGNAL，值为-1，后续节点的线程处于等待状态，而当前节点的线程如果释放了同步状态或者被取消，将会通知后续节点，使后续节点的线程得以运行；
CONDITION，值为-2，节点在条件队列中，节点线程等待在Condition上，当其他线程对Condition调用了signal()方法后，该节点将会从条件队列中转移到同步队列中，加入到对同步状态的获取中；
PROPAGATE，值为-3，表示下一次共享式同步状态获取将会无条件地传播下去
	 */
	/**
	* 如果线程启动有序
	* 1.张飞启动,其他线程等待
	* 张飞线程正在运行
	* AQS同步队列：header(null,-1)----->关羽(-1)----->赵云(0)----->黄忠(0)----->马超（tail,0）
	* 条件队列：空
	* 
	* 2.张飞执行await，张飞释放锁，关羽获取锁
	* 关羽正在运行
	* AQS同步队列：header(null,-1)----->赵云(-1)----->黄忠(0)----->马超（tail,0）
	* 条件队列：张飞（first,-2）------>张飞（last,-2)
	* 
	* 3.关羽执行await,关羽释放锁，赵云获取锁
	* 赵云正在运行
	* AQS同步队列：header(null,-1)----->黄忠(-1)----->马超（tail,0）
	* 条件队列：张飞（first,-2）------>关羽（last,-2)
	* 
	* 4.如果赵云执行signal，赵云正在运行
	* AQS同步队列：header(null,-1)----->黄忠(-1)----->马超（0)----->张飞（tail,0）张飞移动到了队伍的尾巴
	* 条件队列：关羽（first,-2）------>关羽（last,-2)
	* 
	* 如果赵云执行signalAll，赵云正在运行
	* AQS同步队列：header(null,-1)----->黄忠(-1)----->马超（0)------>张飞（0）------>关羽（tail,0)
	 */
	public static void  main (String[] args) throws InterruptedException
	{
		new Thread(new Task(),"张飞").start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(new Task(),"关羽").start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(()->{
			lock.lock();
			condition.signalAll();
		},"赵云") .start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(new Task(),"黄忠").start();
		TimeUnit.SECONDS.sleep(2);
		
		new Thread(new Task(),"马超").start();
	}
	
	static class Task implements Runnable
	{

		@Override
		public void run() 
		{
			String name = Thread.currentThread().getName();
			lock.lock();
			System.out.println(name+":"+"获取🔒");
			try 
			{
				System.out.println(name+":"+"条件等待");
				condition.await();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				lock.unlock();
			}
		}
	}
}
