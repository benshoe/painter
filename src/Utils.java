//
// The Painter application's Open/Save dialog builds
// on approaches as explained in in Oracle's Java Tutorials.
// This file, Utils.java, is based on:
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemo2Project/src/components/Utils.java
// It was adapted only to match our supported file extension,
// namely .painter

import java.io.File;
import javax.swing.ImageIcon;

/* Utils.java is used by FileChooserDemo2.java. */
public class Utils {
    public final static String painter = "painter";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
