package chapter06.collection;

import java.util.concurrent.SynchronousQueue;

import org.junit.Test;

/**
 * 1.无容量的容器
 * 2.SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必须等待一个take操作，否则不能继续添加元素。
  *3.它支持公平访问队列。默认情况下线程采用非公平性策略访问队列。使用以下构造方法可以创建公平性访问的SynchronousQueue，如果设置为true，则等待的线程会采用先进先出的顺序访问队列。
  *4.SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费者线程。队列本身并不存储任何元素，非常适合传递性场景。
  *5.SynchronousQueue的吞吐量高于LinkedBlockingQueue和ArrayBlockingQueue
 */
public class Test_06_07_SynchronousQueue 
{
	private final static SynchronousQueue<String> sync = new SynchronousQueue<String>();
	
	/**
	 * 测试add
	 */
	@Test
	//Queue full
	public void add()
	{
		sync.add("a");
	}
	
	public void test() throws InterruptedException
	{
		sync.put("e");//一直阻塞直到有线程take
	}
}
