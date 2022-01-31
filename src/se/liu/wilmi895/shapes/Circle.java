package se.liu.wilmi895.shapes;

import java.awt.Color;
import java.util.Objects;

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

    @Override public boolean equals(final Object o) {
	if (this == o) {
	    return true;
	}

	if (o == null || getClass() != o.getClass() || !super.equals(o)) {
	    return false;
	}

	final Circle circle = (Circle) o;
	return radius == circle.radius;
    }

    @Override public int hashCode() {
	return Objects.hash(super.hashCode(), radius);
    }
}
