import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ben on 22-02-16.
 */
public final class ComponentPanel extends JPanel {

    private ActionListener onClearClicked;
    private ActionListener onUndoClicked;
    private ActionListener onRedoClicked;

    private JButton undoButton; // button to undo the last shape drawn
    private JButton redoButton; // button to redo the last shape removed
    private JButton clearButton; // button to clear all shapes

    public ComponentPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create a button for clearing the last drawing
        undoButton = new JButton("Undo");
        undoButton.setToolTipText("Remove the latest added shape");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onUndoClicked.actionPerformed(e);
            }
        });
        add(undoButton);

        redoButton = new JButton("Redo");
        redoButton.setToolTipText("Add the latest remove shape");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRedoClicked.actionPerformed(e);
            }
        });
        add(redoButton);

        // create a button for clearing all drawings
        clearButton = new JButton("Clear");
        clearButton.setToolTipText("Clear all images");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClearClicked.actionPerformed(e);
            }
        });
        add(clearButton);

    }

    public void setOnUndoClicked(ActionListener onUndoClicked) {
        this.onUndoClicked = onUndoClicked;
    }

    public void setOnRedoClicked(ActionListener onRedoClicked) {
        this.onRedoClicked = onRedoClicked;
    }

    public void setOnClearClicked(ActionListener onClearClicked) {
        this.onClearClicked = onClearClicked;
    }
}
