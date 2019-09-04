package chapter09.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
 *
 *线程永远存活，并且永远只有一个线程运行
 */
public class Test_09_07_SingleThreadPool {
	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			final int j = i;
			service.execute(()->{
				
				System.out.println(j + " " + Thread.currentThread().getName());
			});
		}
			
	}
}
