package se.liu.wilmi895.shapes;

import java.awt.Color;
import java.util.Objects;

public class Text extends AbstractShape
{
    private int size;
    private String text;

    public Text(final int x, final int y, final int size, final Color color, final String text) {
	super(x, y, color);
	this.size = size;
	this.text = text;
    }

    @Override public String toString() {
	return "Text{" + "x=" + x + ", y=" + y + ", size=" + size + ", color=" + color + ", text='" + text + '\'' + '}';
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

	final Text text1 = (Text) o;
	return size == text1.size && Objects.equals(text, text1.text);
    }

    @Override public int hashCode() {
	return Objects.hash(super.hashCode(), size, text);
    }
}
