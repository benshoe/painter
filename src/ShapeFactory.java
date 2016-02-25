import java.awt.*;

/**
 * @author <a href="mailto:ben.schoen@online.liverpool.ac.uk">Ben Schoen</a>
 * @since 2/25/16.
 */
public final class ShapeFactory {

	public static MyShape getShape(int shapeType, int x1, int y1, int x2, int y2, Color currentColor, boolean filledShape) {
		MyShape currentShape = null;
		switch (shapeType) {
			case 0:
				currentShape = new MyLine(x1, y1, x2, y2, currentColor);
				break;
			case 1:
				currentShape = new MyOval(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case 2:
				currentShape = new MyCircle(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case 3:
				currentShape = new MyRect(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case 4:
				currentShape = new MySquare(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case 5:
				currentShape = new MyRoundedRect(x1, y1, x2, y2, currentColor, filledShape);
				break;
		} // end switch
		return currentShape;
	}

}
