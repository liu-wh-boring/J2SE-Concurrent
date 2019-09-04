package chapter09.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Callable和Runnable一样，不一样
 * 可以有返回值
 * 可以抛出异常
 *
 */
public class Test_09_01_Callable implements Callable<Integer>
{

	@Override
	public Integer call() throws Exception 
	{
		return 1;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		  ExecutorService executor = Executors.newFixedThreadPool(2);  
		  Future<Integer> future = executor.submit(new Test_09_01_Callable());
		  System.out.println(future.get());
	}

}
