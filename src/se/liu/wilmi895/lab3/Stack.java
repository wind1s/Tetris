package se.liu.wilmi895.lab3;

import se.liu.wilmi895.lab1.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stack extends ListManipulator
{
    public void push(final Person person) {
	elements.add(person);
    }

    public Person pop() {
	return elements.remove(size() - 1);
    }
}
