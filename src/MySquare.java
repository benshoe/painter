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

    @Override
    public boolean isTouched(int x, int y) {
        /*
        * Whether the cursor is within the square must be calculated
        * because when drawing a square the user can draw a straight line
        * but the shape will be a square nevertheless. In order to calculate
        * if the mouse is within the square we must calculate the exact dimensions.
        */
        int side = Math.abs(getX1() - getX2());
        int startX = Math.min(getX1(), getX1());
        int startY = Math.min(getY1(), getY2());
        int endX = startX + side;
        int endY = startY + side;

        return x >= startX && x <= endX && y >= startY && y <= endY;
    }
} // end class MySquare
