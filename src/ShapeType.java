/**
 * An enum with all the shape types that this application knows about
 *
 */
public enum ShapeType {
	LINE("Line"), OVAL("Oval"), CIRCLE("Circle"), RECTANGLE("Rectangle"), SQUARE("Square"), ROUNDED_RECTANGLE("Rounded Rectangle");

	private final String shapeName;

	ShapeType(String s) {
		shapeName = s;
	}

	@Override
	public String toString() {
		return shapeName;
	}
}
