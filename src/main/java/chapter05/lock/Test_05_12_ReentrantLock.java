package chapter05.lock;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class Test_05_12_ReentrantLock {

	/**
	 * 测试重入锁
	 */
	@Test
	public void test()
	{
		//公平锁
		ReentrantLock lock = new ReentrantLock(true);
		
		lock.lock();
		lock.lock();
		System.out.println(lock.getHoldCount());//有多少次锁
		
		//多次获取一次释放可不可以
		lock.unlock();
		System.out.println(lock.isHeldByCurrentThread());
		
		//显然不行
		lock.unlock();
		System.out.println(lock.isHeldByCurrentThread());
	}

}
