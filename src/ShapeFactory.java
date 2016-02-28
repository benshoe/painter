import java.awt.*;

/**
 * @author <a href="mailto:ben.schoen@online.liverpool.ac.uk">Ben Schoen</a>
 * @since 2/25/16.
 */
public final class ShapeFactory {

	public static MyShape getShape(ShapeType shapeType, int x1, int y1, int x2, int y2, Color currentColor, boolean filledShape) {
		MyShape currentShape = null;
		switch (shapeType) {
			case LINE:
				currentShape = new MyLine(x1, y1, x2, y2, currentColor);
				break;
			case OVAL:
				currentShape = new MyOval(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case CIRCLE:
				currentShape = new MyCircle(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case RECTANGLE:
				currentShape = new MyRect(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case SQUARE:
				currentShape = new MySquare(x1, y1, x2, y2, currentColor, filledShape);
				break;
			case ROUNDED_RECTANGLE:
				currentShape = new MyRoundedRect(x1, y1, x2, y2, currentColor, filledShape);
				break;
			default:
				throw new IllegalArgumentException("This shape is not known: " + shapeType);
		} // end switch
		return currentShape;
	}

}
