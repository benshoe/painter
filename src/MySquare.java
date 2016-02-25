// GCS Exercise 10.2 Solution: MyRect.java
// Declaration of class MyRect.
import java.awt.*;

public class MySquare extends MyBoundedShape
{
    // call default superclass constructor
    public MySquare()
    {
        super();
    } // end MySquare no-argument constructor

    // call superclass constructor passing parameters
    public MySquare(int x1, int y1, int x2, int y2,
                    Color color, boolean isFilled)
    {
        super(x1, y1, x2, y2, color, isFilled);
    } // end MySquare constructor

    // draw square
    public void draw(Graphics g)
    {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getColor());
        graphics2D.setStroke(new BasicStroke(getStrokeWidth()));

        if (isFilled())
            graphics2D.fillRect(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getWidth());
        else
            graphics2D.drawRect(getUpperLeftX(), getUpperLeftY(),
                    getWidth(), getWidth());
    } // end method draw
} // end class MySquare
