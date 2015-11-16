package example;

import processing.core.*;
import processing.video.*;
import controlP5.*;

public class Main extends PApplet {

    static boolean toggle_cam_bt = false;
    public int vW = 320;
    public int vH = 240;

    static boolean locked = false;

    // Temporarily here because of a bug
    // PImages
    PImage destination;  // Destination image
    PImage blobImg;  // BLOB image

    ControlFrame cf;


    Interface_Controller _interface = new Interface_Controller();
    Point_Processing processP = new Point_Processing();
    Neighbourhood_Processing processN = new Neighbourhood_Processing();

    // ControlP5 variables


    public void setup() {
        size(640, 480);

        // ControlP5
        _interface.init(this);

        noFill();


    }

    public void draw() {
        background(0);

        pushMatrix();
        scale(2);
        popMatrix();

        _interface.cam_handler(this);


    }

    public void captureEvent(Capture c) {
        c.read();
    }

    public void controlEvent(ControlEvent theControlEvent) {
        if (theControlEvent.isFrom("sRange_R")) {
            System.out.println("hello world");
        }
    }


    public void mousePressed() {
        _interface.webcam.mousePressed(mouseX, mouseY, mouseButton);
    }

    public void mouseDragged() {
        _interface.webcam.mouseDragged(mouseX, mouseY, mouseButton);

    }

    public void mouseReleased() {
        locked = false;
    }

}