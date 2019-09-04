package chapter04.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.junit.Test;

public class Test_04_01_ManagementFactory 
{
	/**
	 * 获取线程信息
	 */
	@Test
	public void test()
	{
		ThreadMXBean  manager  = ManagementFactory.getThreadMXBean();
		ThreadInfo[] info = manager.dumpAllThreads(false, false);
		for(ThreadInfo i : info)
		{
			System.out.println(i);
		}
	}
	
	@Test
	public void test2()
	{
		MemoryMXBean manager = ManagementFactory.getMemoryMXBean();
		MemoryUsage heap = manager.getHeapMemoryUsage();
		System.out.println(heap.getUsed());
	}
}
