package se.liu.wilmi895.shapes;

import java.awt.Graphics;
import java.awt.Color;

public interface Shape
{
    int getX();

    int getY();

    Color getColor();

    void draw(final Graphics g);
}
