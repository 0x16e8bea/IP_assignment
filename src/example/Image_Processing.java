package example;

/**
 * Created by Mikkel on 15-Nov-15.
 */

import processing.video.Capture;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class Image_Processing {

    int[] out;
    Color[] c;
    final int vW, vH;

    Image_Processing(Capture video) {
        this.c = new Color[video.pixels.length];
        this.vW = video.width;
        this.vH = video.height;
    }

    //region POINT_PROCESSING
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
                r = this.c[i].getRed() / (float) (this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
                g = this.c[i].getGreen() / (float) (this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
                b = this.c[i].getBlue() / (float) (this.c[i].getRed() + this.c[i].getGreen() + this.c[i].getBlue()) * 255;
            } else {
                r = 0;
                g = 0;
                b = 0;
            }

            out[i] = new Color((int) r, (int) g, (int) b).getRGB();
        }
        return this;
    }

    // Threshold values
    public int sValue_R_min = 0;
    public int sValue_R_max = 255;
    public int sValue_G_min = 0;
    public int sValue_G_max = 255;
    public int sValue_B_min = 0;
    public int sValue_B_max = 255;

    public Image_Processing threshold() {

        convertToRGB(this.out);

        for (int i = 0; i < c.length; i++) {
            if (
                            this.c[i].getRed()      >=  sValue_R_min
                            && this.c[i].getRed()   <  sValue_R_max
                            && this.c[i].getGreen() >=  sValue_G_min
                            && this.c[i].getGreen() <  sValue_G_max
                            && this.c[i].getBlue()  >=  sValue_B_min
                            && this.c[i].getBlue()  <  sValue_B_max
                    ) {
                this.out[i] = Color.white.getRGB();
            } else
                this.out[i] = Color.black.getRGB();
        }
        return this;
    }

    public Image_Processing threshold(int offset, int r, int g, int b) {

        this.sValue_R_min = r - offset;
        this.sValue_R_max = r + offset;
        this.sValue_G_min = g - offset;
        this.sValue_G_max = g + offset;
        this.sValue_B_min = b - offset;
        this.sValue_B_max = b + offset;

        threshold();

        return this;

    }

        //endregion

    //region NEIGHBOURHOOD_PROCESSING
    public Image_Processing erosion(final int radius) throws ExecutionException, InterruptedException {

        RunnableFuture f = new FutureTask(new Callable<int[]>() {
            @Override
            public int[] call() throws Exception {
                convertToRGB(out);

                for (int j = radius/2; j < vH - radius/2; j++) {
                    for (int i = radius/2; i < vW - radius/2; i++) {

                        int sum = 0;

                        for (int kx = -radius/2; kx <= radius/2; kx++) {
                            for (int ky = -radius/2; ky <= radius/2; ky++) {
                                sum += c[(i + kx) + (j * vW) + (ky * vW)].getRed();
                            }
                        }

                        if (sum >= 255 * Math.pow(radius,2)) {
                            out[i + j * vW] = Color.white.getRGB();
                        }
                        else
                            out[i + j * vW] = Color.black.getRGB();
                    }
                }
                return out;
            }
            // implement call
        });

        new Thread(f).start();

        this.out = (int[])f.get();



        return this;
    }

    public Image_Processing dilation(int radius) {

        convertToRGB(this.out);

        for (int j = radius/2; j < vH - radius/2; j++) {
            for (int i = radius/2; i < vW - radius/2; i++) {

                int sum = 0;

                for (int kx = -radius/2; kx <= radius/2; kx++) {
                    for (int ky = -radius/2; ky <= radius/2; ky++) {
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

    //endregion

}
