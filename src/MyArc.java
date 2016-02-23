import java.awt.*;

/**
 * @author <a href="mailto:benshoe@gmail.com">Ben Schoen</a>
 * @since 2/23/16.
 */
public class MyArc extends MyShape {
	public MyArc() {
		super();
	}

	public MyArc(int x1, int y1, int x2, int y2, Color currentColor) {
		super(x1, y1, x2, y2, currentColor);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawArc(getX1(), getY1(), getX2(), getY2(), getX1()-getY1(), getX2()-getY2());
	}
}
