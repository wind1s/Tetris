package se.liu.wilmi895.shapes;

import java.awt.Color;

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

    @Override public void draw() {
	System.out.println("Ritar: " + this);
    }
}
