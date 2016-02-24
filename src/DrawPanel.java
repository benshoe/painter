// GCS Exercise 14.17 Solution: DrawPanel.java
// JPanel that allows the user to draw shapes with the mouse.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DrawPanel extends JPanel {

   	ArrayList<MyShape> shapes = new ArrayList<>(); // Array holding all shapes of drawing
    ArrayList<MyShape> removedShapes = new ArrayList<>();
   private String m_fileBaseName = "New file.painter"; //UGLY// copy in DrawFrame.Java

   private int shapeType; // the type of shape to draw
   private MyShape currentShape; // the current shape being drawn
   private Color currentColor; // the color of the shape
   private boolean filledShape; // whether this shape is filled
    private LineThickness lineThickness = LineThickness.THIN;

   private JLabel statusLabel; // label displaying mouse coordinates

   // constructor
   public DrawPanel(JLabel status)
   {

      setShapeType(0); // initially draw lines
      setDrawingColor(Color.BLACK); // start drawing with black
      setFilledShape(false);// not filled by default
      currentShape = null; // not drawing anything initially

      setBackground(Color.WHITE); // set a white background

      // add the mouse listeners
      MouseHandler mouseHandler = new MouseHandler();
      addMouseListener(mouseHandler);
      addMouseMotionListener(mouseHandler);

      // set the status label for displaying mouse coordinates
      statusLabel = status;
   } // end DrawPanel constructor

   // draw shapes using polymorphism
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);

       Graphics2D graphics2D = (Graphics2D) g;

       // Set anti-alias!
       graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

       // Set anti-alias for text
       graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      for (MyShape s : shapes) {
        s.draw(g);
      }

      if (currentShape != null)
         currentShape.draw(g);
   } // end method paintComponent

   // sets the type of shape to draw
   public void setShapeType(int shapeType)
   {
      if (shapeType < 0 || shapeType > 2)
         shapeType = 0;

      this.shapeType = shapeType;
   } // end method setShapeType

   // sets the drawing color
   public void setDrawingColor(Color c)
   {
      currentColor = c;
   } // end method setDrawingColor

    public void setLineThickness(LineThickness lineThickness) {
        this.lineThickness = lineThickness;
    }

   // clears the last shape drawn
   public void clearLastShape()
   {
      if (shapes.size() > 0)
      {
          MyShape removedShape = shapes.remove(shapes.size() - 1);
          removedShapes.add(removedShape);
          repaint();
      } // end if
   } // end method clearLastShape

    public void redoLastRemovedShape() {
        if(removedShapes.size() == 0) {
            return;
        }
        MyShape lastRemovedShape = removedShapes.remove(removedShapes.size() - 1);
        shapes.add(lastRemovedShape);
        repaint();
    }

   // clears all drawings on this panel
   public void clearDrawing()
   {
       removedShapes.addAll(shapes);
       shapes = new ArrayList<>();
      repaint();
   } // end method clearDrawing

   // sets whether to draw a filled shape
   public void setFilledShape(boolean isFilled)
   {
      filledShape = isFilled;
   } // end method setFilledShape

   // open file chooser to open existing file
  public void openFileDialog() {
    // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
    final JFileChooser fc = new JFileChooser();
    fc.setFileFilter(new ImageFilter());
    int returnVal = fc.showOpenDialog(this);
    String fileNameToOpen;
    File file;
    if (returnVal == JFileChooser.APPROVE_OPTION) {
          file = fc.getSelectedFile();
          fileNameToOpen = file.getPath();
      } else {
          return;
    }

    try {
      // Read from disk using FileInputStream
      FileInputStream f_in = new FileInputStream(fileNameToOpen);

      // Read object using ObjectInputStream
      ObjectInputStream obj_in = new ObjectInputStream (f_in);

      // Read an object
      // FIXME? This throws a compile-time warning, as input is unchecked.
      // What about bad/wrong/corrupt files? Catch them somehow?
      shapes = (ArrayList<MyShape>) obj_in.readObject();
      obj_in.close();
      repaint();
      System.out.println("Opened "+fileNameToOpen+", shapeCount: " + shapes.size());

      // ugly to have such a side-effect in here?
      // if so, how to better change window/JFrame's title?
      JFrame myWindow = (JFrame)SwingUtilities.getRoot(this);
      myWindow.setTitle("Painter - " + file.getName());
    } catch (FileNotFoundException fne) {
      System.out.println("Cannot: " + fne);
    } catch (IOException ioe) {
      System.out.println("Cannot2: " + ioe);
    } catch (ClassNotFoundException cnfe) {
      System.out.println("Cannot3: " + cnfe);
    }
  }

  // I'll add a saveFileToDisk(String filePath) which includes
  // the below try{} block... to make it available for a saveButton().
  // (where saveButton will only be enabled if saved before)

  public void saveFileDialog() {
    System.out.println("SAVE clicked");

    final JFileChooser fc = new JFileChooser();
    fc.setFileFilter(new ImageFilter());
    fc.setSelectedFile(new File(m_fileBaseName));

    int returnVal = fc.showSaveDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      String selectedFileAbsolutPath = fc.getSelectedFile().getAbsolutePath();
      String selectedFileBasename = fc.getSelectedFile().getName();
      String extension = Utils.getExtension(new File(selectedFileAbsolutPath));
      // forcefully append our file extension if needed
      if (extension == null || !extension.equals(Utils.painter)) {
        selectedFileAbsolutPath = selectedFileAbsolutPath + ".painter";
        selectedFileBasename = selectedFileBasename + ".painter";
      }

      try {
        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(selectedFileAbsolutPath);
        // UPDATE title bar with filename? Keep track of unsaved changes?
        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
        // Write object out to disk
        obj_out.writeObject ( shapes );
        obj_out.flush();
        obj_out.close();
        m_fileBaseName = selectedFileBasename;

        // ugly to have such a side-effect in here?
        // if so, how to better change window/JFrame's title?
        JFrame myWindow = (JFrame)SwingUtilities.getRoot(this);
        myWindow.setTitle("Painter - " + selectedFileBasename);
        // might setDirectory(  fc.getSelectedFile().get_?_Directory()  )
        System.out.println("Saved  "+selectedFileAbsolutPath+", shapeCount: "+shapes.size());
      } catch (IOException ioe) {
        System.out.println("Cannot2");
      }
    }

   }

    // handles mouse events for this JPanel
   private class MouseHandler extends MouseAdapter
      implements MouseMotionListener
   {
      // creates and sets the initial position for the new shape
      public void mousePressed(MouseEvent e)
      {
         if (currentShape != null)
            return;

         // create the appropriate shape based on shapeType
         switch (shapeType)
         {
            case 0:
               currentShape = new MyLine(e.getX(), e.getY(),
                  e.getX(), e.getY(), currentColor);
               break;
            case 1:
               currentShape = new MyOval(e.getX(), e.getY(),
                  e.getX(), e.getY(), currentColor, filledShape);
               break;
            case 2:
               currentShape = new MyRect(e.getX(), e.getY(),
                  e.getX(), e.getY(), currentColor, filledShape);
               break;
         } // end switch
          currentShape.setStroke(getStroke());
      } // end method mousePressed

      // fixes the current shape onto the panel
      public void mouseReleased(MouseEvent e)
      {
         if (currentShape == null)
            return;

         // set the second point on the shape
         currentShape.setX2(e.getX());
         currentShape.setY2(e.getY());

         // set the shape only if there is room in the array
         // as we switched to arrayList, this is only limited to system memory...
         shapes.add(currentShape);

         currentShape = null; // clear the temporary drawing shape
         repaint();
      } // end method mouseReleased

      // update the shape to the current mouse position while dragging
      public void mouseDragged(MouseEvent e)
      {
         if (currentShape != null)
         {
            currentShape.setX2(e.getX());
            currentShape.setY2(e.getY());
            repaint();
         } // end if

         mouseMoved(e); // update status bar
      } // end method mouseDragged

      // updates the status bar to show the current mouse coordinates
      public void mouseMoved(MouseEvent e)
      {
         statusLabel.setText(
            String.format("(%d,%d)", e.getX(), e.getY()));
      } // end method mouseMoved
   } // end class MouseHandler

    private BasicStroke getStroke() {
        return new BasicStroke(lineThickness.getThickness());
    }
} // end class DrawPanel


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
