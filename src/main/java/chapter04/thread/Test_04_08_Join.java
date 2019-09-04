package chapter04.thread;

/**
 * 1.如果一个当前线程A执行了B.join()语句，其含义是：当前线程A等待B线程终止之后才从B.join()返回
 * 2.了join(long millis)和join(longmillis,int nanos)两个具备超时特性的方法
 */
public class Test_04_08_Join 
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread t = new Thread(()->{
			for(int i=0 ;i<1000000;i++);
			System.out.println("t线程结束");
		}) ;
		t.start();
		t.join();//当前主线程等待t线程结束后继续执行
		System.out.println("主线程继续运行");
	}
}
