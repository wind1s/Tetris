package se.liu.wilmi895.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Objects;

public class Rectangle extends AbstractShape
{
    private int width;
    private int height;

    public Rectangle(final int x, final int y, final int width, final int height, final Color color) {
	super(x, y, color);
	this.width = width;
	this.height = height;
    }

    @Override public String toString() {
	return "Rectangle{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", color=" + color +
	       '}';
    }

    @Override public void draw(final Graphics g) {
	g.setColor(color);
	g.drawRect(x, y, width, height);
    }

    @Override public boolean equals(final Object o) {
	if (this == o) {
	    return true;
	}

	if (o == null || getClass() != o.getClass() || !super.equals(o)) {
	    return false;
	}

	final Rectangle rectangle = (Rectangle) o;
	return width == rectangle.width && height == rectangle.height;
    }

    @Override public int hashCode() {
	return Objects.hash(super.hashCode(), width, height);
    }
}
