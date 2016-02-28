// GCS Exercise 10.2 Solution: MyLine.java
// Declaration of class MyLine.
import java.awt.*;

public class MyLine extends MyShape
{
   // call default superclass constructor
   public MyLine()
   {
      super();
   } // end MyLine no-argument constructor

   // call superclass constructor passing parameters
   public MyLine(int x1, int y1, int x2, int y2, Color color)
   {
      super(x1, y1, x2, y2, color);
   } // end MyLine constructor

   /**
   * isTouched(x,y) reports true if x,y isTouched MyLine(x1,y1,x2,y2)
   * <p>
   * Overrides MyShape.isTouched() in order to provide a more precise
   * "shape discovery" than when just using the line's bounding box.
   *
   * @param  int X coordinate of point to test
   * @param  int Y coordinate of point to test
   * @return boolean true, if the given point is on MyLine
   */
   @Override
   public boolean isTouched(int x, int y) {
      // return false if given point is outside line's bounding box
      if (  ( x < getX1() && x < getX2() ) ||
            ( y < getY1() && y < getY2() ) ||
            ( x > getX1() && x > getX2() ) ||
            ( y > getY1() && y > getY2() )
        ) {
        return false;
      }
      // https://www.mathsisfun.com/algebra/line-equation-2points.html
      int changeInY = (getY1()-getY2());
      int chnageInX = (getX1()-getX2());
      float slope = (float)changeInY / (float)chnageInX;
      // point-slope formula: y - y1 = m(x - x1)
      // https://www.mathsisfun.com/algebra/line-equation-point-slope.html
      // left and right part of equation:
      float left  = y - getY1();
      float right = slope * (x - getX1());

      // To have left==right, user must click exactly on points...
      // thus we allow for slight deviation here.
      // Might need additional tuning...
      int maxDeviation = 10;
      if (Math.abs(left-right) < maxDeviation) {
        System.out.println("PointSlopeEquation match: " +
        left + " = " + right + " => Deviation: "+Math.abs(left-right));
        return true;
      }
      // default: no match / does not tangent
      return false;
   }

   // draw line in specified color
   public void draw(Graphics g)
   {
      Graphics2D graphics2D = (Graphics2D) g;
      graphics2D.setColor(getColor());
      graphics2D.setStroke(new BasicStroke(getStrokeWidth()));

      g.setColor(getColor());
      g.drawLine(getX1(), getY1(), getX2(), getY2());
   } // end method draw
} // end class MyLine


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
