package chapter04.thread;

/**
 * 线程的优先级
 1）线程的调度
现代操作系统基本采用分时的形式调度运行的线程，操作系统会分出一个个时间片，线
程会分配到若干时间片，当线程的时间片用完了就会发生线程调度，并等待着下次分配。线程
分配到的时间片多少也就决定了线程使用处理器资源的多少，而线程优先级就是决定线程需
要多或者少分配一些处理器资源的线程属性。

2）优先级设置
setPriorty效果不明显
操作系统可以完全不用理会Java线程对于优先级的设定
 *
 */
public class Test_04_02_Priority {

	public static void main(String[] args) 
	{
		for(int i=1;i<=10;i++)
		{
			final int b = i;
			Thread t = new Thread()
			{
				public void run()
				{
					
					long now = System.currentTimeMillis();
					for(int k=100000;k>=0;k--)
					{
						
					}
					System.out.println(b+":"+(System.currentTimeMillis()-now));
				}
			};
			
			t.setPriority(i);
			t.start();
		}

	}

}
