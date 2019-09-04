package chapter04.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * 
 *1.Daemon线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持性工作。
 *2.这意味着，当一个Java虚拟机中不存在非Daemon线程的时候，Java虚拟机将会退出。
 *3.可以通过调用Thread.setDaemon(true)将线程设置为Daemon线程。
 *4.Daemon属性需要在启动线程之前设置，不能在启动线程之后设置
 *5.当你在一个守护线程中产生了其他线程，那么这些新产生的线程不用设置Daemon属性，都将是守护线程，用户线程同样
 *
 */
public class Test_04_05_Deamon 
{
	public static void main(String[] args)
	{
		Thread t = new Thread()
		{
			@Override
			public void run() {
				System.out.println(Thread.currentThread().isDaemon());
				while(true);
			}
		};
		
		t.setDaemon(true);
		t.start();
		
		//测试守护线程的子线程是否是守护线程
		Thread t1 = new Thread()
		{
			@Override
			public void run() {
				
				Thread child = new Thread()
				{
					@Override
					public void run() {
						System.out.println("子线程="+Thread.currentThread().isDaemon());
					}
				};
				child.start();
				
			}
		};
		
		t1.setDaemon(true);
		t1.start();
		
		System.out.println("deamon线程测试");
		LockSupport.park();
	}
}
