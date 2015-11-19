package example;

import processing.video.Capture;

import java.awt.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Neighbourhood_Processing {

    int[] out;
    int vW, vH;

    Color[] c;

    Neighbourhood_Processing(Capture video) {
        this.c = new Color[video.pixels.length];
        this.vW = video.width;
        this.vH = video.height;
    }






}
