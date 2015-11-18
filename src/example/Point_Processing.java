package example;

/**
 * Created by Mikkel on 15-Nov-15.
 */

import processing.core.*;

import java.awt.*;

public class Point_Processing {

    int[] out;

    Point_Processing(int[] c) {
        this.out = new int[c.length];
    }

        public Point_Processing normalize(int[] c) {

            for (int i = 0; i < c.length; i++) {

                float r, g, b;

                if (new Color(c[i]).getRed() + new Color(c[i]).getGreen() + new Color(c[i]).getBlue() != 0) {
                    r = new Color(c[i]).getRed() / (float)(new Color(c[i]).getRed() + new Color(c[i]).getGreen() + new Color(c[i]).getBlue()) * 255;
                    g = new Color(c[i]).getGreen() / (float)(new Color(c[i]).getRed() + new Color(c[i]).getGreen() + new Color(c[i]).getBlue()) * 255;
                    b = new Color(c[i]).getBlue() / (float)(new Color(c[i]).getRed() + new Color(c[i]).getGreen() + new Color(c[i]).getBlue()) * 255;
                } else {
                    r = 0;
                    g = 0;
                    b = 0;
                }

                out[i] = new Color((int)r,(int)g,(int)b).getRGB();
            }
            return this;
        }

    public Point_Processing test(int[] c) {

        for (int i = 0; i < c.length; i++) {

            int r = new Color(c[i]).getRed();
            int g = new Color(c[i]).getGreen();
            int b = new Color(c[i]).getBlue();

            out[i] = new Color(r, g, b).getRGB();
        }
        return this;
    }


//        public Color[] threshold(Color[] c) {
//
//            Color[] out = new Color[c.length];
//
//            for (int i = 0; i < c.length; i++) {
//                if (c[i].getRed() >= interfaceController.sValue_R_min && red(c[i]) <= interfaceController.sValue_R_max
//                        && c[i].getGreen() >= interfaceController.sValue_G_min && green(c[i]) <= interfaceController.sValue_G_max
//                        && c[i].getBlue() >= interfaceController.sValue_B_min && blue(c[i]) <= interfaceController.sValue_B_max) {
//                    out[i] = Color.WHITE;
//                } else
//                    out[i] = Color.BLACK;
//            }
//
//            return out;
//        }
}
