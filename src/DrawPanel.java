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
    private String fileBaseName = "New file.painter"; //UGLY// copy in DrawFrame.Java

    private ShapeType shapeType; // the type of shape to draw
    private MyShape currentShape; // the current shape being drawn
    private Color currentColor; // the color of the shape
    private boolean filledShape; // whether this shape is filled
    private LineThickness lineThickness = LineThickness.THIN;
    private JLabel statusLabel; // label displaying mouse coordinates

    /* We have two MouseMotionListener classes in this class created where at any time only one is active.
     * The MouseDrawHandler is active by default and is used to draw shapes on the screen.
     * The MouseSelectHandler is used to select an image on the screen and move it across the screen.
     *
     * The mouse handlers can be toggled by the user by clicking on the image with the pencil and
     * the image below it (a pencil in a dashed square).
     *
     * The setDrawMode and setSelectMode methods take care of this. Also the cursor changes its appearance depending on the mode
     *
     */
    private final MouseDrawHandler mouseDrawHandler;
    private final MouseSelectHandler mouseSelectHandler;

    // constructor
    public DrawPanel(JLabel status) {

        setShapeType(ShapeType.LINE); // initially draw lines
        setDrawingColor(Color.BLACK); // start drawing with black
        setFilledShape(false);// not filled by default
        currentShape = null; // not drawing anything initially

        setBackground(Color.WHITE); // set a white background

        // add the mouse listeners
        mouseDrawHandler = new MouseDrawHandler();
        mouseSelectHandler = new MouseSelectHandler();
        setDrawMode();

        // set the status label for displaying mouse coordinates
        statusLabel = status;
    } // end DrawPanel constructor

    // draw shapes using polymorphism
    public void paintComponent(Graphics g) {
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

    public void setDrawMode() {
        addMouseListener(mouseDrawHandler);
        addMouseMotionListener(mouseDrawHandler);
        removeMouseListener(mouseSelectHandler);
        removeMouseMotionListener(mouseSelectHandler);
        setFrameCursor(Cursor.CROSSHAIR_CURSOR);
        currentShape = null; // when switching mode the currentshape must be set to null
    }

    public void setSelectMode() {
        addMouseListener(mouseSelectHandler);
        addMouseMotionListener(mouseSelectHandler);
        removeMouseListener(mouseDrawHandler);
        removeMouseMotionListener(mouseDrawHandler);
        setFrameCursor(Cursor.DEFAULT_CURSOR);
        currentShape = null; // when switching mode the currentshape must be set to null or we would move the last selected image to the cursor
    }

    // sets the type of shape to draw
    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    } // end method setShapeType

    // sets the drawing color
    public void setDrawingColor(Color c) {
        currentColor = c;
    } // end method setDrawingColor

    public void setLineThickness(LineThickness lineThickness) {
        this.lineThickness = lineThickness;
    }

    private float getStrokeWidth() {
        return (float) lineThickness.getThickness();
    }

    // clears the last shape drawn by removing it from list shapes and adding it to removedShapes for redo
    public void clearLastShape() {
        if (shapes.size() == 0) {
            return;
        }
        MyShape removedShape = shapes.remove(shapes.size() - 1);
        removedShapes.add(removedShape);
        repaint();
    } // end method clearLastShape

    // By moving the shape out of removedShapes and putting it in shapes, we redo the latest action
    public void redoLastRemovedShape() {
        if (removedShapes.size() == 0) {
            return;
        }
        MyShape lastRemovedShape = removedShapes.remove(removedShapes.size() - 1);
        shapes.add(lastRemovedShape);
        repaint();
    }

    // clears all drawings on this panel
    public void clearDrawing() {
        removedShapes.addAll(shapes);
        shapes = new ArrayList<>();
        repaint();
    } // end method clearDrawing

    // sets whether to draw a filled shape
    public void setFilledShape(boolean isFilled) {
        filledShape = isFilled;
    } // end method setFilledShape

    /**
     * openFileDialog pops up a JFileChooser and reads user-selected file.
     * <p>
     * The chooser is built on Java Tutorials by Oracle, namely:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
     * The ImageFilter() below ensures only .painter files are user-selectable;
     * see ImageFilter.java and Utils.java.
     */
    public void openFileDialog() {

        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new ImageFilter());
        int returnVal = fc.showOpenDialog(this);
        String fileNameToOpen;
        File file;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            fileNameToOpen = file.getPath();
        } else {
            // early exit if user selected cancel button
            return;
        }

        try {
            // Read from disk using FileInputStream
            FileInputStream f_in = new FileInputStream(fileNameToOpen);

            // Read object using ObjectInputStream
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Slurp file contents into member shapes and close input file
            shapes = (ArrayList<MyShape>) obj_in.readObject();
            obj_in.close();
            repaint();

            // update window title: show filename
            JFrame myWindow = (JFrame) SwingUtilities.getRoot(this);
            myWindow.setTitle("Painter - " + file.getName());
            System.out.println("Opened " + fileNameToOpen + ", shapeCount: " + shapes.size());
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null,
                    "Error opening file: " + ioe.getMessage(),
                    "File I/O Error!",  JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null,
                    "Error opening file: " + cnfe.getMessage(),
                    "File Format Error!",  JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * saveToFile saves (ArrayList) shapes to file, using serialization
     * <p>
     * The output file will contain the shapes only, without any further
     * meta information (like file format version or other "header infos").
     *
     */
    public void saveToFile(String filename) throws IOException {
        // Write to disk with FileOutputStream
        FileOutputStream f_out = new FileOutputStream(filename);
        // UPDATE title bar with filename? Keep track of unsaved changes?
        // Write object with ObjectOutputStream
        ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
        // Write object out to disk
        obj_out.writeObject(shapes);
        obj_out.flush();
        obj_out.close();
    }

    /**
     * saveFileDialog pops up a FileChooser to let user save current image.
     * <p>
     * If the selected file exists, the user will be asked whether he wants
     * to overwrite it. If saving was successful, window title will be
     * set to now-current filename.
     *
     */
    public void saveFileDialog() {

        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new ImageFilter());
        fc.setSelectedFile(new File(fileBaseName));

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

            File testOutfile = new File(selectedFileAbsolutPath);
            if (testOutfile.exists()) {
                Object[] options = {"Yes, Overwrite",
                                    "No, abort saving"};
                int userReply = JOptionPane.showOptionDialog(null,
                    "Selected file already exists. Overwrite?",
                    "File exists",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);

                if (userReply == 1) {
                    System.out.println("Saving aborted by user, outfile exists");
                    // user said to not overwrite -- early exit!
                    return;
                }
            }

            try {
                saveToFile(selectedFileAbsolutPath);
                fileBaseName = selectedFileBasename;

                // append new / now-current filename to window title
                JFrame myWindow = (JFrame) SwingUtilities.getRoot(this);
                myWindow.setTitle("Painter - " + selectedFileBasename);

                System.out.println("Saved  " + selectedFileAbsolutPath + ", shapeCount: " + shapes.size());
            } catch (IOException ioe) {
                // given output file may not be writable...
                JOptionPane.showMessageDialog(null,
                    "Error saving file: " + ioe.getMessage(),
                    "Error!",  JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    // handles mouse draw events for this JPanel
    private class MouseDrawHandler extends MouseAdapter
            implements MouseMotionListener {
        // creates and sets the initial position for the new shape
        public void mousePressed(MouseEvent e) {
            if (currentShape != null)
                return;

            // create the appropriate shape based on shapeType
            currentShape = ShapeFactory.getShape(shapeType, e.getX(), e.getY(), e.getX(), e.getY(), currentColor, filledShape);
            currentShape.setStrokeWidth(getStrokeWidth());
        } // end method mousePressed

        // fixes the current shape onto the panel
        public void mouseReleased(MouseEvent e) {
            if (currentShape == null)
                return;

            // set the second point on the shape
            currentShape.setX2(e.getX());
            currentShape.setY2(e.getY());

            // we add the shape to the arrayList shapes
            shapes.add(currentShape);

            currentShape = null; // clear the temporary drawing shape
            repaint();
        } // end method mouseReleased

        // update the shape to the current mouse position while dragging
        public void mouseDragged(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setX2(e.getX());
                currentShape.setY2(e.getY());
                repaint();
            } // end if

            mouseMoved(e); // update status bar
        } // end method mouseDragged

        // updates the status bar to show the current mouse coordinates
        public void mouseMoved(MouseEvent e) {
            statusLabel.setText(String.format("(%d,%d)", e.getX(), e.getY()));
        } // end method mouseMoved
    } // end class MouseHandler

    // handles mouse select/drag events for this JPanel
    // we extend the MouseAdapter so we only need to implement the methods that we need
    private class MouseSelectHandler extends MouseAdapter
            implements MouseMotionListener {

        private int startX1; // we need to store the initial values
        private int startX2; // of the shape so we can move it
        private int startY1; // around and keep its shape intact
        private int startY2;
        private int mouseX;
        private int mouseY;

        @Override
        public void mouseMoved(MouseEvent e) {
            statusLabel.setText(String.format("(%d,%d)", e.getX(), e.getY()));
            for(MyShape shape : shapes) {
                if (shape.isTouched(e.getX(), e.getY())) { //The first shape that is 'touched' will be moved
                    currentShape = shape;
                    setFrameCursor(Cursor.HAND_CURSOR); // change the cursor type
                    return;
                }
            }
            setFrameCursor(Cursor.DEFAULT_CURSOR);
            currentShape = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            if(currentShape != null) {
                // store the initial values of the shape
                startX1 = currentShape.getX1();
                startX2 = currentShape.getX2();
                startY1 = currentShape.getY1();
                startY2 = currentShape.getY2();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setFrameCursor(Cursor.DEFAULT_CURSOR);
            currentShape = null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(currentShape != null) {
                // The shape's size must be kept constant or we would shrink the image while dragging
                currentShape.setX1(startX1 + e.getX() - mouseX);
                currentShape.setY1(startY1 + e.getY() - mouseY);
                currentShape.setX2(startX2 + e.getX() - mouseX);
                currentShape.setY2(startY2 + e.getY() - mouseY);
                repaint();
            }
        }
    }

    private void setFrameCursor(int cursorType) {
        Cursor cursor = new Cursor(cursorType);
        DrawPanel.this.setCursor(cursor);
    }

} // end class DrawPanel


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 * *
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
