// GCS Exercise 10.2 Solution: MyRect.java
// Declaration of class MyRect.
import java.awt.*;

public class MyRoundedRect extends MyBoundedShape
{
    // call default superclass constructor
    public MyRoundedRect()
    {
        super();
    } // end MyRoundedRect no-argument constructor

    // call superclass constructor passing parameters
    public MyRoundedRect(int x1, int y1, int x2, int y2,
                           Color color, boolean isFilled)
    {
        super(x1, y1, x2, y2, color, isFilled);
    } // end MyRoundedRect constructor

    // draw Rounded Rect
    public void draw(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));

        if (isFilled())
            graphics2D.fillRoundRect(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getHeight(), 10, 10);
        else
            graphics2D.drawRoundRect(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getHeight(), 10, 10);
    } // end method draw
} // end class MyRoundedRect
