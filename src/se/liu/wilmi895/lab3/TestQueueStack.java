package se.liu.wilmi895.lab3;

import se.liu.wilmi895.lab1.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestQueueStack
{
    public static void main(String[] args) {
	final Person[] people =
		{ new Person("William", LocalDate.of(2001, 3, 5)), new Person("Ferdinand", LocalDate.of(2002, 7, 4)),
			new Person("Olle", LocalDate.of(2002, 8, 7)), new Person("Robot", LocalDate.of(2000, 6, 9)),
			new Person("?????", LocalDate.of(1972, 2, 29)) };
	TestQueueStack.testStack(people);
	TestQueueStack.testQueue(people);
    }

    public static void testStack(final Person[] people) {
	System.out.println("Testing Stack Class");
	Stack stack = new Stack();

	for (Person person : people) {
	    stack.push(person);
	}

	while (!stack.isEmpty()) {
	    System.out.println(stack.pop());
	}
    }

    public static void testQueue(final Person[] people) {
	System.out.println("Testing Queue Class");
	Queue queue = new Queue();

	for (Person person : people) {
	    queue.enqueue(person);
	}

	while (!queue.isEmpty()) {
	    System.out.println(queue.dequeue());
	}
    }
}
