package chapter07.atomic;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.junit.Test;

public class Test_07_04_Field 
{
	static class User
	{
		public volatile String name;
		//更新类的字段（属性）必须使用public volatile修饰符
		public volatile int num;
		public volatile long length;
		
		User(String name)
		{
			this.name = name ;
			this.num = 0 ;
		}
		
		public String toString() 
		{
			return "name="+name+","+"num="+num;
		}
	}
	
	@Test
	public void testAtomicIntegerFieldUpdater() throws InterruptedException
	{
		AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "num");
		Semaphore semaphore = new Semaphore(0);
		User user = new User("刘百万");
		new Thread(()->{
			for(int i=0;i<1000000;i++)
			updater.getAndIncrement(user);
			semaphore.release();
			}) .start();
		
		new Thread(()->{
			for(int i=0;i<1000000;i++)
			updater.getAndIncrement(user);
			semaphore.release();
			}) .start();
		
		new Thread(()->{
			for(int i=0;i<1000000;i++)
			updater.getAndIncrement(user);
			semaphore.release();
			}) .start();
		semaphore.acquire(3);
		System.out.println(user);
	}
	
	@Test
	public void testMethod()
	{
		AtomicReferenceFieldUpdater<User,String> afu = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
		System.out.println(afu);
		
		AtomicLongFieldUpdater<User> alu = AtomicLongFieldUpdater.newUpdater(User.class, "length");
		System.out.println(alu);
		
		AtomicStampedReference<User> asr = new AtomicStampedReference<User>(new User("zhangsan"), 0);
		System.out.println(asr);
	}
}
