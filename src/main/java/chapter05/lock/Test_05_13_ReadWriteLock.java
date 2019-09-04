package chapter05.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

public class Test_05_13_ReadWriteLock 
{
	/**
	 * 读锁共享
	 */
	@Test
	public void test()
	{
		ReadWriteLock  rwLock = new ReentrantReadWriteLock ();
		Lock read = rwLock.readLock();
		
		new Thread(()->{
			read.lock();
			System.out.println("XX获取锁");
		}) .start();
		
		read.lock();
		System.out.println("OO获取锁");
		
		LockSupport.park();
	}
	
	/**
	 * 写锁互斥
	 * 
	 */
	@Test
	public void test1()
	{
		ReadWriteLock  rwLock = new ReentrantReadWriteLock ();
		Lock write = rwLock.writeLock();
		
		new Thread(()->{
			write.lock();
			System.out.println("XX获取锁");
		}) .start();
		
		write.lock();
		System.out.println("OO获取锁");
		
		LockSupport.park();
	}
	
	/**
	 *读锁和写锁的互斥
	 *	写锁没有优先级
	 */
	@Test
	public void test2() throws InterruptedException
	{
		ReadWriteLock  rwLock = new ReentrantReadWriteLock ();
		Lock write = rwLock.writeLock();
		Lock read = rwLock.readLock();
		
		new Thread(()->{
			read.lock();
			System.out.println("XX获取锁");
			
			try {
				Thread.sleep(5000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}) .start();
		
		Thread.sleep(2000);
		write.lock();
		System.out.println("OO获取锁");
		
		LockSupport.park();
	}
	
	/**
	 * 锁降级
	 * 锁降级指的是写锁降级成为读锁。如果当前线程拥有写锁，然后将其释放，最后再获取读
锁，这种分段完成的过程不能称之为锁降级。锁降级是指把持住（当前拥有的）写锁，再获取到
读锁，随后释放（先前拥有的）写锁的过程。
在一边读一边写的情况下提高性能
	 */
	@Test
	public void test3() throws InterruptedException
	{
		ReadWriteLock  rwLock = new ReentrantReadWriteLock ();
		Lock write = rwLock.writeLock();
		Lock read = rwLock.readLock();
		
		new Thread(()->{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			read.lock();
			System.out.println("XX获取锁");
			
		}) .start();
		
		write.lock();
		System.out.println("OO获取写锁");
		read.lock();
		write.unlock();
		
		System.out.println("OO获取读锁");
		
		LockSupport.park();
	}
}
