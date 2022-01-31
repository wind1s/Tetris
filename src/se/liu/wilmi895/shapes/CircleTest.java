package se.liu.wilmi895.shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CircleTest
{
    public static void main(String[] args) {
	final List<Circle> circles = new ArrayList<>();
	circles.add(new Circle(0, 0, 2, Color.BLACK));
	circles.add(new Circle(5, 5, 5, Color.BLUE));
	circles.add(new Circle(-1, 3, 1, Color.RED));

	for (Circle circle : circles) {
	    System.out.println(circle.getX() + ", " + circle.getY());
	}
    }
}
