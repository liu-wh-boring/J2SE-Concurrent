package chapter10.Patten;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 　Master-Worker模式是常用的并行模式之一，
 * 它的核心思想是：系统由两类进程协同工作，即Master进程和Worker进程，Master负责接收和分配任务，
 * Wroker负责处理子任务。当各个Worker进程将子任务处理完成后，
 * 将结果返回给Master进程，由Master进程进行汇总，从而得到最终的结果
 *
 */
public class Test_10_03_WorkMasterPatten 
{
	
	public static void  main(String[] args)
	{
		Task[] t = new Task[10];
		for(int i=0;i<10;i++)
		{
			t[i] = new ThreeTask(i);
		}
		
		Master master = new Master(2,t);
		master.start();
		
		while(true)
		{
			if(master.isOver())
			{
				System.out.println(master.result);
				break;
			}
		}
	}
	
	/**
	 *雇主
	 */
	static class Master
	{
		/**
		 * 任务队列
		 */
		private final LinkedBlockingQueue<Task> task = new LinkedBlockingQueue<Task>();
		
		/**
		 *奴隶队列
		 */
		private final Map<String,Thread> worker = new HashMap<String,Thread>();
		
		/**
		 * 执行结果
		 */
		private final Map<Task,Integer> result = new ConcurrentHashMap<Task,Integer>(); 
		
		public Master(int workers,Task... tasks)
		{
			while(workers-->0)
			{
				worker.put("奴隶-"+workers, new Thread(new Worker(this)));
			}
			
			for(Task i : tasks)
			{
				task.add(i);
			}
		}
		
		public void start()
		{
			for(Thread i : worker.values())
			{
				i.start();
			}
		}
		
		public boolean isOver() 
		{
			for(Thread i : worker.values())
			{
				if(i.isAlive())
				{
					return false;
				}
			}
			return true;
		}
		
		
		public Task getTask()
		{
			return task.poll();
		}
		
		public void putResult(Task t,Integer r)
		{
			result.put(t, r);
		}
	}
	
	/**
	 * 奴隶
	 *
	 */
	static class Worker implements Runnable
	{
		private final Master master;
		
		Worker(Master master)
		{
			this.master = master;
		}

		@Override
		public void run() 
		{
			while(true)
			{
				Task task = master.getTask();
				if(task == null)
				{
					break;
				}
				else
				{
					master.putResult(task, task.handelr());
				}
			}
		}
		
	}
	
	static interface Task
	{
		Integer handelr();
	}
	
	static class ThreeTask implements Task
	{

		private int i;
		
		ThreeTask(int i)
		{
			this.i= i;
		}
		
		@Override
		public Integer handelr() 
		{
			 return i * i * i;
		}
		
		@Override
		public String toString()
		{
			return "taks-"+i;
		}
	}
}
