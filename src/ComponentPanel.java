import javax.swing.*;
import java.awt.event.*;

/**
 * Created by ben on 22-02-16.
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

        Icon drawModeIcon = new ImageIcon(getClass().getResource(Images.DRAW));
        JButton drawModeButton = PainterPanel.addButtonToPanel(this, drawModeIcon, "Draw shape");
        drawModeButton.addActionListener(e -> onDrawModeClicked.actionPerformed(e));

        Icon selectModeIcon = new ImageIcon(getClass().getResource(Images.SELECT));
        JButton selectModeButton = PainterPanel.addButtonToPanel(this, selectModeIcon, "Select shape");
        selectModeButton.addActionListener(e -> onSelectModeClicked.actionPerformed(e));

        Icon undoIcon = new ImageIcon(getClass().getResource(Images.UNDO));
        JButton undoButton = PainterPanel.addButtonToPanel(this, undoIcon, "Undo");
        undoButton.addActionListener(e -> onUndoClicked.actionPerformed(e));

        Icon redoIcon = new ImageIcon(getClass().getResource(Images.REDO));
        JButton redoButton = PainterPanel.addButtonToPanel(this, redoIcon, "Redo");
        redoButton.addActionListener(e -> onRedoClicked.actionPerformed(e));

        // create a button for clearing all drawings
        Icon clearIcon = new ImageIcon(getClass().getResource(Images.TRASH));
        JButton clearButton = PainterPanel.addButtonToPanel(this, clearIcon, "Clear all images");
        clearButton.addActionListener(e -> onClearClicked.actionPerformed(e));

        Icon colorIcon = new ImageIcon(getClass().getResource(Images.COLOUR_PICKER));
        JButton colorbutton = PainterPanel.addButtonToPanel(this, colorIcon, "Draw shape");
        colorbutton.addActionListener(e -> onColorClicked.actionPerformed(e));

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
