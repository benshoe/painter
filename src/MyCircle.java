// GCS Exercise 10.2 Solution: MyOval.java
// Declaration of class MyOval.
import java.awt.*;

public class MyCircle extends MyBoundedShape
{
    // call default superclass constructor
    public MyCircle()
    {
        super();
    } // end MyCircle no-argument constructor

    // call superclass constructor passing parameters
    public MyCircle(int x1, int y1, int x2, int y2,
                  Color color, boolean isFilled)
    {
        super(x1, y1, x2, y2, color, isFilled);
    } // end MyCircle constructor

    // draw circle
    public void draw(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));

        if (isFilled())
            graphics2D.fillOval(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getWidth());
        else
            graphics2D.drawOval(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getWidth());
    } // end method draw
} // end class MyCircle
