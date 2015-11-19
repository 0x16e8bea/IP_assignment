package example;

/**
 * Created by Mikkel on 15-Nov-15.
 */

import processing.video.Capture;

import java.awt.*;

public class Image_Processing {

    int[] out;
    Color[] c;
    int vW, vH;

    Image_Processing(Capture video) {
        this.c = new Color[video.pixels.length];
        this.vW = video.width;
        this.vH = video.height;
    }

        public void convertToRGB(int[] c) {

            for (int i = 0; i < c.length; i++) {
                int r = new Color(c[i]).getRed();
                int g = new Color(c[i]).getGreen();
                int b = new Color(c[i]).getBlue();

                this.c[i] = new Color(r, g, b);
            }
        }

    public Image_Processing run(int[] c) {
        this.out = c.clone();
        return this;
    }

    public Image_Processing normalize() {

        convertToRGB(this.out);
        for (int i = 0; i < this.out.length; i++) {

            float r, g, b;

            if (this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue() != 0) {
                r = this.c[i].getRed() / (float)(this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
                g = this.c[i].getGreen() / (float)(this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
                b = this.c[i].getBlue() / (float)(this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
            } else {
                r = 0;
                g = 0;
                b = 0;
            }

            out[i] = new Color((int)r,(int)g,(int)b).getRGB();
        }
        return this;
    }

        public Image_Processing threshold() {

            convertToRGB(this.out);

            for (int i = 0; i < c.length; i++) {
                if (
                           this.c[i].getRed()               >= 0
                                   && this.c[i].getRed()    <= 255
                        && this.c[i].getGreen()             >= 50
                                   && this.c[i].getGreen()  <= 100
                        && this.c[i].getBlue()              >= 20
                                   && this.c[i].getBlue()   <= 150)
                {
                    this.out[i] = Color.white.getRGB();
                } else
                    this.out[i] = Color.black.getRGB();
            }
            return this;
        }

    public Image_Processing erosion() {

        convertToRGB(this.out);

        for (int j = 1; j < vH - 1; j++) {
            for (int i = 1; i < vW - 1; i++) {

                int sum = 0;

                for (int kx = -1; kx <= 1; kx++) {
                    for (int ky = -1; ky <= 1; ky++) {
                        sum += this.c[(i + kx) + (j * vW) + (ky * vW)].getRed();
                    }
                }

                if (sum < 255*9)
                    this.out[i + j * vW] = Color.white.getRGB();
                else
                    this.out[i + j * vW] = Color.black.getRGB();
            }
        }

        return this;
    }

    public Image_Processing dilation() {

        convertToRGB(this.out);

        for (int j = 1; j < vH - 1; j++) {
            for (int i = 1; i < vW - 1; i++) {

                int sum = 0;

                for (int kx = -1; kx <= 1; kx++) {
                    for (int ky = -1; ky <= 1; ky++) {
                        sum += this.c[(i + kx) + (j * vW) + (ky * vW)].getRed();
                    }
                }

                if (sum <= 255)
                    this.out[i + j * vW] = Color.black.getRGB();
                else
                    this.out[i + j * vW] = Color.white.getRGB();
            }
        }

        return this;
    }
}
