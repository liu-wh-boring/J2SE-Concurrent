package chapter08.tool;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Test_08_03_Semaphore 
{
	@Test
	public void testSemaphore() throws InterruptedException
	{
		int permits = 5;
		
		Semaphore semaphore = new Semaphore(permits,true);
		
		semaphore.acquire(); //获取许可证,如果获取到了就继续向下走,否则就阻塞 中断敏感的
		semaphore.acquire(4);//获取多个许可证
		
		semaphore.acquireUninterruptibly();//中断不敏感
		semaphore.acquireUninterruptibly(permits);
		
		semaphore.tryAcquire();//尝试获取
		semaphore.tryAcquire(permits);//尝试获取
		
		semaphore.tryAcquire(10, TimeUnit.SECONDS);//超时获取
		semaphore.tryAcquire(permits, 10, TimeUnit.SECONDS);
		
		semaphore.release();//释放许可证
		semaphore.release(permits);
	}
}
