// GCS Exercise 14.17 Solution: DrawPanel.java
// JPanel that allows the user to draw shapes with the mouse.
import java.io.*; // Just inputStream and OutputStream required...
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame; // ugly?
import javax.swing.SwingUtilities; // ugly2?

public class DrawPanel extends JPanel
{
   ArrayList<MyShape> shapes; // Array holding all shapes of drawing
   private int shapeCount; // total number of shapes
   private String m_fileBaseName = "New file.painter"; //UGLY// copy in DrawFrame.Java

   private int shapeType; // the type of shape to draw
   private MyShape currentShape; // the current shape being drawn
   private Color currentColor; // the color of the shape
   private boolean filledShape; // whether this shape is filled

   private JLabel statusLabel; // label displaying mouse coordinates

   // constructor
   public DrawPanel(JLabel status)
   {
      shapes = new ArrayList<MyShape>();
      shapeCount = 0; // initially we have no shapes

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

   // clears the last shape drawn
   public void clearLastShape()
   {
      if (shapeCount > 0)
      {
         shapeCount--;
         repaint();
      } // end if
   } // end method clearLastShape

   // clears all drawings on this panel
   public void clearDrawing()
   {
      shapeCount = 0;
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
      shapeCount = shapes.size();
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
        System.out.println("Saved  "+selectedFileAbsolutPath+", shapeCount: "+shapeCount);
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
         shapeCount++;

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
