import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This helper class adds buttons to a panel.
 */
public class PainterPanel {

    public static JButton addButtonToPanel(JPanel panel, URL iconUrl, String toolTipText) {
        JButton button;
        button = new JButton(new ImageIcon(iconUrl));
        button.setToolTipText(toolTipText);
        button.setPreferredSize(new Dimension(40, 40));
        panel.add(button);
        return button;
    }
}
