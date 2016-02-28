// GCS Exercise 14.17 Solution: DrawFrame.java
// Program that creates a panel for the user to draw shapes.
// Allows the user to choose the shape and color.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawFrame extends JFrame
   implements ItemListener
{
    // Array of possible shapes
    private DrawPanel drawPanel; // panel that handles the drawing

    private JButton openFileButton; // button to open existing image
    private JButton saveFileButton; // button to save to file
    private JComboBox<ShapeType> shapeChoices; // combo box for selecting shapes
    private JCheckBox filledCheckBox; // check box to toggle filled shapes
    private JComboBox<LineThickness> basicStrokeCombo;

    // constructor
   public DrawFrame()
   {
       super("Painter - New file.painter");

       setLookAndFeel();

       // create a panel to store the components at the top of the frame
       JPanel topPanel = new JPanel();
       ComponentPanel componentPanel = new ComponentPanel();
      /*
      The anonymous inner class is an object that implements the ActionListener
interface. The object’s actionPerformed() method is overridden to set the frame’s
title when the corresponding button is clicked. Because each button has its own listener,
it’s simpler than using one listener for multiple interface components.
Inner classes look more complicated than separate classes, but they can simplify and
shorten your Java code. [Lemay, L. & Cadenhead, R. 2002, Sams teach yourself Java 2 in 21 days, Sams.]
       */
       componentPanel.setOnDrawModeClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               drawPanel.setDrawmode();
           }
       });
       componentPanel.setOnSelectModeClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               drawPanel.setSelectmode();
           }
       });
       componentPanel.setOnUndoClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               drawPanel.clearLastShape();
           }
       });
       componentPanel.setOnClearClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
            drawPanel.clearDrawing();
         }
       });
       componentPanel.setOnRedoClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
            drawPanel.redoLastRemovedShape();
         }
       });
       componentPanel.setOnColorClicked(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
            drawPanel.setDrawingColor((Color) e.getSource());
         }
       });

       //Save and open buttons
       Icon openIcon = new ImageIcon(getClass().getResource(Images.OPEN));
       openFileButton = new JButton(openIcon);
       openFileButton.addActionListener(e -> drawPanel.openFileDialog());
       openFileButton.setToolTipText("Open File");
       openFileButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(openFileButton);

       Icon saveIcon = new ImageIcon(getClass().getResource(Images.SAVE));
       saveFileButton = new JButton(saveIcon);
       saveFileButton.addActionListener(e -> drawPanel.saveFileDialog());
       saveFileButton.setToolTipText("Save File");
       saveFileButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(saveFileButton);

       //Draw shape buttons
       Icon lineIcon = new ImageIcon(getClass().getResource(Images.LINE));
       JButton drawLineButton = new JButton(lineIcon);
       drawLineButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.LINE));
       drawLineButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawLineButton);

       Icon squareIcon = new ImageIcon(getClass().getResource(Images.SQUARE));
       JButton drawSquareButton = new JButton(squareIcon);
       drawSquareButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.SQUARE));
       drawSquareButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawSquareButton);

       Icon rectIcon = new ImageIcon(getClass().getResource(Images.RECTANGLE));
       JButton drawRectButton = new JButton(rectIcon);
       drawRectButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.RECTANGLE));
       drawRectButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawRectButton);

       Icon circleIcon = new ImageIcon(getClass().getResource(Images.CIRCLE));
       JButton drawCirleButton = new JButton(circleIcon);
       drawCirleButton.addActionListener(e -> drawPanel.setShapeType(ShapeType.CIRCLE));
       drawCirleButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawCirleButton);

       Icon ovalIcon = new ImageIcon(getClass().getResource(Images.OVAL));
       JButton drawOvalButton = new JButton(ovalIcon);
       drawOvalButton.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawOvalButton);

       Icon roundedRectIcon = new ImageIcon(getClass().getResource(Images.ROUNDED_RECT));
       JButton drawRoundedSquare = new JButton(roundedRectIcon);
       drawRoundedSquare.addActionListener(e -> drawPanel.setShapeType(ShapeType.ROUNDED_SQUARE));
       drawRoundedSquare.setPreferredSize(new Dimension(40, 40));
       topPanel.add(drawRoundedSquare);

       //Line thinkness Button
       LineThickness[] lineThicknesses = LineThickness.values();
       basicStrokeCombo = new JComboBox<>(lineThicknesses);
       topPanel.add(basicStrokeCombo);
       basicStrokeCombo.addActionListener(e -> {
          final Object selectedItem = basicStrokeCombo.getSelectedItem();
          drawPanel.setLineThickness((LineThickness) selectedItem);
       });



      // create a combobox for choosing shapes
      shapeChoices = new JComboBox<>(ShapeType.values());
      shapeChoices.addItemListener(this);
      topPanel.add(shapeChoices);

      // create a checkbox to determine whether the shape is filled
      filledCheckBox = new JCheckBox("Filled");
      filledCheckBox.addItemListener(this);
      topPanel.add(filledCheckBox);

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
         drawPanel.setShapeType((ShapeType) shapeChoices.getSelectedItem());
      else if (e.getSource() == filledCheckBox) // filled/unfilled
         drawPanel.setFilledShape(filledCheckBox.isSelected());
   } // end method itemStateChanged

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
