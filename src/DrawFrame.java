// GCS Exercise 14.17 Solution: DrawFrame.java
// Program that creates a panel for the user to draw shapes.
// Allows the user to choose the shape and color.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawFrame extends JFrame
   implements ItemListener, ActionListener
{
   // Array of possible colors
   private Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN,
      Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
      Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE,
      Color.YELLOW};

   // Array of names corresponding to the possible colors
   private String[] colorNames = {"Black", "Blue", "Cyan", "Dark Gray",
      "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red",
      "White", "Yellow"};

   // Array of possible shapes
   private String[] shapes = {"Line", "Oval", "Rectangle"};

   private DrawPanel drawPanel; // panel that handles the drawing

   private JButton openFileButton; // button to open existing image
   private JButton saveFileButton; // button to save to file
   private JComboBox<String> colorChoices; // combo box for selecting the color
   private JComboBox<String> shapeChoices; // combo box for selecting shapes
   private JCheckBox filledCheckBox; // check box to toggle filled shapes

   // constructor
   public DrawFrame()
   {
      super("Painter - New file.painter");

      setLookAndFeel();

      // create a panel to store the components at the top of the frame
      JPanel topPanel = new JPanel();
      ComponentPanel componentPanel = new ComponentPanel();
      componentPanel.setOnUndoClicked(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            drawPanel.clearLastShape();
         }
      });
      componentPanel.setOnClearClicked(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            drawPanel.clearDrawing();
         }
      });
      componentPanel.setOnRedoClicked(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            drawPanel.redoLastRemovedShape();
         }
      });

      // create a combobox for choosing colors
      colorChoices = new JComboBox<String>(colorNames);
      colorChoices.addItemListener(this);
      topPanel.add(colorChoices);

      // create a combobox for choosing shapes
      shapeChoices = new JComboBox<String>(shapes);
      shapeChoices.addItemListener(this);
      topPanel.add(shapeChoices);

      // create a checkbox to determine whether the shape is filled
      filledCheckBox = new JCheckBox("Filled");
      filledCheckBox.addItemListener(this);
      topPanel.add(filledCheckBox);

      openFileButton = new JButton("Open");
      openFileButton.addActionListener(this);
      topPanel.add(openFileButton);

      saveFileButton = new JButton("Save");
      saveFileButton.addActionListener(this);
      topPanel.add(saveFileButton);

      // add the top panel to the frame
      add(topPanel, BorderLayout.NORTH);
      add(componentPanel, BorderLayout.WEST);

      // create a label for the status bar
      JLabel statusLabel = new JLabel("(0,0)");

      // add the status bar at the bottom
      add(statusLabel, BorderLayout.SOUTH);

      // create the DrawPanel with its status bar label
      drawPanel = new DrawPanel(statusLabel);

      add(drawPanel); // add the drawing area to the center
   } // end DrawFrame constructor

   private void setLookAndFeel() {
      try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
         SwingUtilities.updateComponentTreeUI(this);
      } catch (Exception e) {
         System.out.println("Could not load look and feel " + e);
      }
   }

   // handle selections made to a combobox or checkbox
   public void itemStateChanged(ItemEvent e)
   {
      if (e.getSource() == shapeChoices) // choosing a shape
         drawPanel.setShapeType(shapeChoices.getSelectedIndex());
      else if (e.getSource() == colorChoices) // choosing a color
         drawPanel.setDrawingColor(
            colors[colorChoices.getSelectedIndex()]);
      else if (e.getSource() == filledCheckBox) // filled/unfilled
         drawPanel.setFilledShape(filledCheckBox.isSelected());
   } // end method itemStateChanged

   // handle button clicks
   public void actionPerformed(ActionEvent e)
   {
//      if (e.getSource() == undoButton)
//
//      else if (e.getSource() == clearButton)
//         drawPanel.clearDrawing();
      if (e.getSource() == openFileButton)
        drawPanel.openFileDialog();
      else if (e.getSource() == saveFileButton)
        drawPanel.saveFileDialog();
   } // end method actionPerformed
} // end class DrawFrame


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
