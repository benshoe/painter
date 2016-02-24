/**
 * @author <a href="mailto:ben.schoen@itris.nl">Ben Schoen</a>
 * @since 2/24/16.
 */
public enum LineThickness {
	THIN("Thin", 1), MIDDLE("Middle", 2), THICK("Thick", 3);

	private final String name;
	private final int thickness;

	LineThickness(String name, int thickness) {
		this.name = name;
		this.thickness = thickness;
	}

	int getThickness() {
		return thickness;
	}

	@Override
	public String toString() {
		return name;
	}
}
