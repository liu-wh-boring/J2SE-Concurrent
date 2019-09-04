package chapter06.collection;

/**
 * 1.一个由链表结构组成的无界阻塞队列
 * 2.transfer方法:
 *如果当前有消费者正在等待接收元素（消费者使用take()方法或带时间限制的poll()方法时），transfer方法可以把生产者传入的元素立刻transfer（传输）给消费者。
 *如果没有消费者在等待接收元素，transfer方法会将元素存放在队列的tail节点，并等到该元素被消费者消费了才返回
 *
 *2.tryTransfer方法:
 *是用来试探生产者传入的元素是否能直接传给消费者。如果没有消费者等待接收元素，则返回false。
 *和transfer方法的区别是tryTransfer方法无论消费者是否接收，方法立即返回，而transfer方法是必须等到消费者消费了才返回
 *
 *3.tryTransfer（E e，long timeout，TimeUnit unit）方法
 *试图把生产者传入的元素直接传给消费者，但是如果没有消费者消费该元素则等待指定的时间再返回，
 *如果超时还没消费元素，则返回false，如果在超时时间内消费了元素，则返回true
 *
 */
public class Test_06_10_LinkedTransferQueue {

}
