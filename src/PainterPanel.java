import javax.swing.*;
import java.awt.*;

/**
 * Created by ben on 28-02-16.
 */
public class PainterPanel {

    public static JButton addButtonToPanel(JPanel panel, Icon icon, String toolTipText) {
        JButton button;
        button = new JButton(icon);
        button.setToolTipText(toolTipText);
        button.setPreferredSize(new Dimension(40, 40));
        panel.add(button);
        return button;
    }
}
