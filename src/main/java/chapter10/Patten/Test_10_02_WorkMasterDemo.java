package chapter10.Patten;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test_10_02_WorkMasterDemo 
{
	static class Master 
	{
	    // 任务队列
	    protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
	    
	    // Worker进程队列
	    protected Map<String, Thread> threadMap = new HashMap<String, Thread>();
	    
	    // 子任务处理结果集
	    protected Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
	    
	    // 构造函数
	    public Master(Worker worker, int countWorker) 
	    {
	        worker.setWorkQueue(workQueue); //添加任务队列
	        worker.setResultMap(resultMap); //添加计算结果集合
	        for(int i=0; i<countWorker; i++) 
	        {
	            threadMap.put(Integer.toString(i), new Thread(worker, Integer.toString(i))); //循环添加任务进程
	        }
	    }
	    
	    //是否所有的子任务都结束了
	    public boolean isComplete() 
	    {
	        for(Map.Entry<String, Thread> entry : threadMap.entrySet())
	        {
	            if(entry.getValue().getState() != Thread.State.TERMINATED)
	                return false; //存在未完成的任务
	        }
	        return true;
	    }
	    
	    //提交一个子任务
	    public void submit(Object job) 
	    {
	        workQueue.add(job);
	    }
	    
	    //返回子任务结果集
	    public Map<String, Object> getResultMap() 
	    {
	        return resultMap;
	    }
	    
	    //执行所有Worker进程，进行处理
	    public void execute() 
	    {
	        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) 
	        {
	            entry.getValue().start();
	        }
	    }
	}

	 static  class Worker  implements Runnable
	  {
		  
	    //任务队列，用于取得子任务
	    protected Queue<Object> workQueue;
	    
	    //子任务处理结果集
	    protected Map<String ,Object> resultMap;
	    
	    public void setWorkQueue(Queue<Object> workQueue)
	    {
	        this.workQueue= workQueue;
	    }
	    
	    public void setResultMap(Map<String ,Object> resultMap)
	    {
	        this.resultMap=resultMap;
	    }
	    
	    //子任务处理的逻辑，在子类中实现具体逻辑
	    public Object handle(Object input)
	    {
	        return input;
	    }
	    
	    @Override
	    public void run() 
	    {
	        while(true)
	        {
	            //获取子任务
	            Object input= workQueue.poll();
	            if(input==null)
	            {
	                break;
	            }
	            //处理子任务
	            Object re = handle(input);
	            resultMap.put(Integer.toString(input.hashCode()), re);
	        }
	    }
	}
	  
	//求立方和
	  static class PlusWorker extends Worker 
	  { 
		    @Override
		    public Object handle(Object input) {
		        int i = (Integer)input;
		        return i * i * i;
		    }
		}
	 
	  public static void main(String[] args) 
	  {
	        //固定使用5个Workde
	        Master master = new Master(new PlusWorker(), 5);
	        for(int i=1; i<=100; i++) //提交100个子任务
	            master.submit(i);
	        master.execute(); //开始计算
	        int re = 0;  //最终计算结果保存在此
	        Map<String, Object> resultMap = master.getResultMap();
	        
	        while(true) {//不需要等待所有Worker都执行完成，即可开始计算最终结果
	            Set<String> keys = resultMap.keySet();  //开始计算最终结果
	            String key = null;
	            for(String k : keys) {
	                key = k;
	                break;
	            }
	            Integer i = null;
	            if(key != null)
	                i = (Integer)resultMap.get(key);
	            if(i != null)
	                re += i; //最终结果
	            if(key != null)
	                resultMap.remove(key); //移除已被计算过的项目
	            if(master.isComplete() && resultMap.size()==0)
	                break;
	        }
	        System.out.println(re);
	    }
}
