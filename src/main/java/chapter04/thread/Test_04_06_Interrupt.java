package chapter04.thread;

import java.util.concurrent.TimeUnit;

/**
 * 1.中断可以理解为线程的一个标识位属性，它表示一个运行中的线程是否被其他线程进行了中断操作。
 * 2.中断好比其他线程对该线程打了个招呼，其他线程通过调用该线程的interrupt()方法对其进行中断操作
 * 3.线程通过检查自身是否被中断来进行响应，线程通过方法isInterrupted()来进行判断是否被中断
 * 4.调用静态方法Thread.interrupted()对当前线程的中断标识位进行复位
 * 5.许多声明抛出InterruptedException的方法在抛出之前，Java虚拟机会先将该线程的中断标识位清除
 */
public class Test_04_06_Interrupt {

	public static void main(String[] args) throws InterruptedException 
	{
		Thread.currentThread().interrupt();
		System.out.println("中断后="+Thread.currentThread().isInterrupted());
		
		Thread.interrupted();
		System.out.println("复位后="+Thread.currentThread().isInterrupted());
		
		Thread t = new Thread(()->{
			try 
			{
				Thread.sleep(1000000);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			try 
			{
				Thread.sleep(1000000);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
		}) ;
		t.start();
		t.interrupt();
		TimeUnit.SECONDS.sleep(3);
		System.out.println("异常后="+t.isInterrupted());
				
	}

}
