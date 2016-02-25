// Based on MyLine.java

import java.awt.*;

public class MyText extends MyShape
{
    // call default superclass constructor
    public MyText()
    {
        super();
    } // end MyLine no-argument constructor

    // call superclass constructor passing parameters
    public MyText(int x1, int y1, int x2, int y2, Color color)
    {
        super(x1, y1, x2, y2, color);
    } // end MyLine constructor

    // draw line in specified color
    public void draw(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));
        // chooser...?
        g.setFont(new Font("Times", Font.PLAIN, 13));
        g.setColor(getColor());
        // note: mouse dragging will modify x2+y2...
        g.drawString("Where/how to enter text? Popup?", getX2(), getY2());
    } // end method draw
} // end class MyLine
