/**
 * @author <a href="mailto:ben.schoen@itris.nl">Ben Schoen</a>
 * @since 2/24/16.
 */
public enum LineThickness {
	THIN("Thin"), MIDDLE("Middle"), THICK("Thick");

	private final String thickness;

	LineThickness(String thickness) {
		this.thickness = thickness;
	}

	@Override
	public String toString() {
		return thickness;
	}
}
