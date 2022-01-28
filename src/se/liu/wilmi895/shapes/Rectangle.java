package se.liu.wilmi895.shapes;

import java.awt.Color;

public class Rectangle implements Shape
{
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public Rectangle(final int x, final int y, final int width, final int height, final Color color) {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.color = color;
    }

    @Override public int getX() {
	return x;
    }

    @Override public int getY() {
	return y;
    }

    @Override public Color getColor() {
	return color;
    }

    @Override public String toString() {
	return "Rectangle{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", color=" + color +
	       '}';
    }

    @Override public void draw() {
	System.out.println("Ritar: " + this);
    }
}
