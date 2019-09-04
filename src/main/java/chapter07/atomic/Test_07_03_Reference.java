package chapter07.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class Test_07_03_Reference 
{
	static class User
	{
		String name;
		String password;
		
		User(String name,String password)
		{
			this.name = name ;
			this.password = password ;
		}
		
		public String toString() 
		{
			return "name="+name+","+"password="+password;
		}
	}
	
	@Test
	public void testAtomicReference()
	{
		AtomicReference<User> ar = new AtomicReference<User>();
		User newValue = new User("张三","zhangsan");
		User u = ar.getAndSet(newValue);
		boolean result = ar.compareAndSet(u, new User("李四","lsi"));
		System.out.println(result);
		System.out.println(ar.get());
		
		result = ar.compareAndSet(newValue, new User("李四","lsi"));
		System.out.println(result);
		System.out.println(ar.get());
	}	
	
	public void testAtomicReferenceFieldUpdater()
	{
		@SuppressWarnings("unused")
		AtomicMarkableReference<User> updater = new AtomicMarkableReference<User>(new User("李四","lsi"), false);
	}
}
