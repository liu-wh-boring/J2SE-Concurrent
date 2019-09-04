package chapter10.Patten;

import java.util.concurrent.TimeUnit;

/**
 * Future相当于中间商，
 *
 */
public class Test_10_01_FuturePatten 
{
	static class Request<T>
	{
		private final FutureT<T> future = new FutureT<T>();
		
		public FutureT<T> request(T t)
		{			
			new Thread() 
			{
				@Override
				public void run() 
				{
					//此处喝很慢
					try
					{
						TimeUnit.SECONDS.sleep(30);
					}
					catch(Exception e)
					{
						
					}
					future.set(t);
				}
				
			}.start();
			
			return future;
		}
	}
	
	static class FutureT<T>
	{
		T outcome;
		volatile boolean  isReady = false;
		
		public synchronized void set(T outcome)
		{
			this.outcome = outcome;
			isReady = true;
			notifyAll();
			
		}
		
		public synchronized T  get() throws InterruptedException
		{
			while(!isReady)
			{
				wait();
			}
			return outcome;
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException 
	{
		Request<String> r = new Request<String>();
		
		FutureT<String> f = r.request("MMNN");
		System.out.println(f.get());
	}

}
