/**
 * @author <a href="mailto:ben.schoen@itris.nl">Ben Schoen</a>
 * @since 2/25/16.
 */
public enum ShapeType {
	LINE("Line"), OVAL("Oval"), CIRCLE("Circle"), RECTANGLE("Rectangle"), SQUARE("Square"), ROUNDED_SQUARE("Rounded Square");

	private final String shapeName;

	ShapeType(String s) {
		shapeName = s;
	}

	public String getShapeName() {
		return shapeName;
	}

	@Override
	public String toString() {
		return shapeName;
	}
}
