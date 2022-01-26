package se.liu.wilmi895.shapes;

import java.awt.Color;

public class Circle implements Shape
{
    private int x;
    private int y;
    private int radius;
    private Color color;

    public Circle(final int x, final int y, final int radius, final Color color) {
        if (radius < 0) {
            throw new IllegalArgumentException("Negative radius!");
        }

        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    @Override public int getX() {
        return x;
    }

    @Override public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    @Override public Color getColor() {
        return color;
    }

    @Override public String toString() {
        return "Circle{" + "x=" + x + ", y=" + y + ", radius=" + radius + ", color=" + color + '}';
    }

    @Override public void draw() {
        System.out.println("Ritar: " + this);
    }
}
