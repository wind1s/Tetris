package se.liu.wilmi895.shapes;

import java.awt.*;
import java.util.ArrayList;

public class ShapeTest
{
    public static void main(String[] args) {
	final ArrayList<Shape> shapes = new ArrayList<>();
	shapes.add(new Circle(0, 0, 2, Color.BLACK));
	shapes.add(new Circle(5, 5, 5, Color.BLUE));
	shapes.add(new Circle(-1, 3, 1, Color.RED));
	shapes.add(new Rectangle(-1, 10, 10, 10, Color.YELLOW));
	shapes.add(new Rectangle(-12, -23, 235, 2, Color.GREEN));
	shapes.add(new Rectangle(23, -2, 10, 34, Color.CYAN));
	shapes.add(new Text(1, 1, 14, Color.GRAY, "NIce meme"));
	shapes.add(new Text(5, 5, 20, Color.BLUE, "Teste texte"));
	shapes.add(new Text(-5, -10, 100, Color.RED, "BIG TEXT"));


	for (Shape shape : shapes) {
	    shape.draw();
	}
    }
}
