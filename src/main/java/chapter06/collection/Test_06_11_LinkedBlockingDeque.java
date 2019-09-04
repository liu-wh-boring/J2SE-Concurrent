package chapter06.collection;

/**
 * LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的是可以
从队列的两端插入和移出元素。双向队列因为多了一个操作队列的入口，在多线程同时入队
时，也就减少了一半的竞争。相比其他的阻塞队列，LinkedBlockingDeque多了addFirst、
addLast、offerFirst、offerLast、peekFirst和peekLast等方法，以First单词结尾的方法，表示插入、
获取（peek）或移除双端队列的第一个元素。以Last单词结尾的方法，表示插入、获取或移除双
端队列的最后一个元素。另外，插入方法add等同于addLast，移除方法remove等效于
removeFirst。但是take方法却等同于takeFirst，不知道是不是JDK的bug，使用时还是用带有First
和Last后缀的方法更清楚
 *
 */
public class Test_06_11_LinkedBlockingDeque {

}
