package chapter10.Patten;

/**
 * 在并行开发过程中，为确保数据的一致性和正确性，又必要对对象进行同步，但是同步操作对系统性能有相当的损耗。因此可以使用一种不可改变的对象，依靠其不变形来确保并行操作在没有同步的情况下依旧保持一致性和正确性。
         不变模式的使用场景主要包括两个条件：
   a. 当对象创建后，其内部状态和数据不再发生任何改变；
   b.对象需求被共享、被多线程频繁访问。
 *
 *在Java语言中，不变模式的实现很简单，只需要注意一下4点：
  a. 去除所有setter方法以及可以修改自身属性的方法；
  b. 将所有属性设置为private的，并用final标记，确保其不可修改；
  c. 确保没有子类可以继承该类；
  d. 有一个可以创建完整对象的构造函数
 */
public class Test_10_04_FinalPatten 
{
	private final String a;  //private确保不可被访问，final确保不可被修改
	private final String b;
	private final double c;
	
	public Test_10_04_FinalPatten(String a, String b, double c) 
	{
		//创建对象时必须指定值，因为创建后将无法修改
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public String getA() {
		return a;
	}
 
	public String getB() {
		return b;
	}
 
	public double getC() {
		return c;
	}
}
