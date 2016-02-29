import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This panel holds the different buttons that handle the actions for color, redo, undo, draw and select mode
 *
 * The ActionListener fields get a function passed from the calling client
 */
public final class ComponentPanel extends JPanel {

    private ActionListener onClearClicked;
    private ActionListener onUndoClicked;
    private ActionListener onRedoClicked;
    private ActionListener onColorClicked;
    private ActionListener onDrawModeClicked;
    private ActionListener onSelectModeClicked;

	public ComponentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton drawModeButton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.DRAW), "Draw shape");
        drawModeButton.addActionListener(e -> onDrawModeClicked.actionPerformed(e));

        JButton selectModeButton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.SELECT), "Select shape");
        selectModeButton.addActionListener(e -> onSelectModeClicked.actionPerformed(e));

        JButton undoButton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.UNDO), "Undo");
        undoButton.addActionListener(e -> onUndoClicked.actionPerformed(e));

        JButton redoButton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.REDO), "Redo");
        redoButton.addActionListener(e -> onRedoClicked.actionPerformed(e));

        // create a button for clearing all drawings
        JButton clearButton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.TRASH), "Clear all images");
        clearButton.addActionListener(e -> onClearClicked.actionPerformed(e));

        JButton colorbutton = PainterPanel.addButtonToPanel(this, getClass().getResource(Images.COLOUR_PICKER), "Draw shape");
        colorbutton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(ComponentPanel.this, "Choose color", Color.BLACK);
            e.setSource(newColor);
            onColorClicked.actionPerformed(e);
        });

	}

    void setOnSelectModeClicked(ActionListener onSelectModeClicked) {
        this.onSelectModeClicked = onSelectModeClicked;
    }

    void setOnDrawModeClicked(ActionListener onDrawModeClicked) {
        this.onDrawModeClicked = onDrawModeClicked;
    }

    void setOnUndoClicked(ActionListener onUndoClicked) {
        this.onUndoClicked = onUndoClicked;
    }

    void setOnRedoClicked(ActionListener onRedoClicked) {
        this.onRedoClicked = onRedoClicked;
    }

    void setOnClearClicked(ActionListener onClearClicked) {
        this.onClearClicked = onClearClicked;
    }

    void setOnColorClicked(ActionListener onColorClicked) {
        this.onColorClicked = onColorClicked;
    }
}
