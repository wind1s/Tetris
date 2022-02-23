package se.liu.wilmi895.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class StartScreenComponent extends JComponent
{
    private final static double IMAGE_SCALE = 0.5;
    public final static int SHOW_TIME_MS = 3000;
    private final ImageIcon startImage = new ImageIcon(ClassLoader.getSystemResource("images/tetris_logo.png"));

    @Override public Dimension getPreferredSize() {
	return new Dimension((int)(IMAGE_SCALE * startImage.getIconWidth()), (int)(IMAGE_SCALE * startImage.getIconHeight()));
    }

    @Override protected void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	final AffineTransform old = g2d.getTransform();
	final AffineTransform at = AffineTransform.getScaleInstance(IMAGE_SCALE, IMAGE_SCALE);
	g2d.setTransform(at);

	startImage.paintIcon(this, g, 0, 0);

	g2d.setTransform(old);
    }
}
