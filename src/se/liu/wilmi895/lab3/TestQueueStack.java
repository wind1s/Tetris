package se.liu.wilmi895.lab3;

import se.liu.wilmi895.lab1.Person;

import java.time.LocalDate;

public class TestQueueStack
{
    public static void main(String[] args) {
	TestQueueStack.testStack();
	TestQueueStack.testQueue();
    }
    
    public static void testStack() {
	Stack stack = new Stack();
	stack.push(new Person("William", LocalDate.of(2001, 3,5)));
	stack.push(new Person("Ferdinand", LocalDate.of(2002, 7, 4)));
	stack.push(new Person("Olle", LocalDate.of(2002, 8, 7)));
	stack.push(new Person("Robot", LocalDate.of(2000, 6,9)));
	stack.push(new Person("?????", LocalDate.of(1972, 2,29)));

	System.out.println("Testing Stack Class");
	while(!stack.isEmpty()) {
	    System.out.println(stack.pop());
	}
    }
    
    public static void testQueue() {
	Queue queue = new Queue();
	queue.enqueue(new Person("William", LocalDate.of(2001, 3,5)));
	queue.enqueue(new Person("Ferdinand", LocalDate.of(2002, 7, 4)));
	queue.enqueue(new Person("Olle", LocalDate.of(2002, 8, 7)));
	queue.enqueue(new Person("Robot", LocalDate.of(2000, 6,9)));
	queue.enqueue(new Person("?????", LocalDate.of(1972, 2,29)));

	System.out.println("Testing Queue Class");
	while(!queue.isEmpty()) {
	    System.out.println(queue.dequeue());
	}
    }
}
