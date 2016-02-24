import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ben on 22-02-16.
 */
public final class ComponentPanel extends JPanel {

    private MouseListener onClearClicked;
    private MouseListener onUndoClicked;
    private MouseListener onRedoClicked;
    private MouseListener onColorClicked;
    private MouseListener onDrawModeClicked;
    private MouseListener onSelectModeClicked;

	public ComponentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Icon drawModeIcon = new ImageIcon(getClass().getResource(Images.OVAL));
        JLabel drawModeLable = new JLabel(drawModeIcon);
        drawModeLable.setToolTipText("Draw mode");
        drawModeLable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onDrawModeClicked.mouseClicked(e);
            }
        });
        add(drawModeLable);

        Icon selectModeIcon = new ImageIcon(getClass().getResource(Images.START));
        JLabel selectModeLable = new JLabel(selectModeIcon);
        selectModeLable.setToolTipText("Select mode");
        selectModeLable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onSelectModeClicked.mouseClicked(e);
            }
        });
        add(selectModeLable);

        Icon undoIcon = new ImageIcon(getClass().getResource(Images.UNDO));
        JLabel undoLabel = new JLabel(undoIcon);
        undoLabel.setToolTipText("Undo");
        undoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onUndoClicked.mouseClicked(e);
            }
        });
        add(undoLabel);

        Icon redoIcon = new ImageIcon(getClass().getResource(Images.REDO));
        JLabel redoLabel = new JLabel(redoIcon);
        redoLabel.setToolTipText("Redo");
        redoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onRedoClicked.mouseClicked(e);
            }
        });
        add(redoLabel);

        // create a label for clearing all drawings
        Icon clearIcon = new ImageIcon(getClass().getResource(Images.TRASH));
        JLabel clearLabel = new JLabel(clearIcon);
        clearLabel.setToolTipText("Clear all images");
        clearLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClearClicked.mouseClicked(e);
            }
        });
        add(clearLabel);

        Icon colorIcon = new ImageIcon(getClass().getResource(Images.TRASH));
        JLabel colorLabel = new JLabel(colorIcon);
        colorLabel.setToolTipText("Choose a color");
        colorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(ComponentPanel.this, "Choose color", Color.BLACK);
                e.setSource(newColor);
                onColorClicked.mouseClicked(e);
            }
        });
        add(colorLabel);

	}

    void setOnSelectModeClicked(MouseListener onSelectModeClicked) {
        this.onSelectModeClicked = onSelectModeClicked;
    }

    void setOnDrawModeClicked(MouseListener onDrawModeClicked) {
        this.onDrawModeClicked = onDrawModeClicked;
    }

    void setOnUndoClicked(MouseListener onUndoClicked) {
        this.onUndoClicked = onUndoClicked;
    }

    void setOnRedoClicked(MouseListener onRedoClicked) {
        this.onRedoClicked = onRedoClicked;
    }

    void setOnClearClicked(MouseListener onClearClicked) {
        this.onClearClicked = onClearClicked;
    }

    void setOnColorClicked(MouseListener onColorClicked) {
        this.onColorClicked = onColorClicked;
    }
}
