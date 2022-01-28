package se.liu.wilmi895.shapes;

import java.awt.Color;

public class Circle extends AbstractShape
{
    private int radius;

    public Circle(final int x, final int y, final int radius, final Color color) {
	super(x, y, color);

	if (radius < 0) {
	    throw new IllegalArgumentException("Negative radius!");
	}

	this.radius = radius;
    }

    public int getRadius() {
	return radius;
    }

    @Override public String toString() {
	return "Circle{" + "x=" + getX() + ", y=" + getY() + ", radius=" + radius + ", color=" + getColor() + '}';
    }

    @Override public void draw() {
	System.out.println("Ritar: " + this);
    }
}
