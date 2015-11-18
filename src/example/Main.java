package example;

import processing.core.*;
import processing.video.*;
import controlP5.*;

public class Main extends PApplet {

    static boolean toggle_cam_bt = false;
    static boolean toggle_out_bt = false;
    static boolean locked = false;

    Interface_Controller _interface = new Interface_Controller();

    public void setup() {
        size(640, 480);
        noFill();

        // ControlP5
        _interface.init(this);

    }

    public void draw() {
        background(0, 10, 30);

        pushMatrix();
        scale(2);
        popMatrix();

        _interface.window_handler(this);

    }

    public void controlEvent(ControlEvent theControlEvent) {
        _interface.controlEvent(theControlEvent);
    }

    public void captureEvent(Capture c) {
        c.read();
    }

    public void mousePressed() {
        _interface.webcam.mousePressed(mouseX, mouseY, mouseButton);
    }

    public void mouseDragged() {
        _interface.webcam.mouseDragged(mouseX, mouseY, mouseButton);
        _interface.output.mouseDragged(mouseX, mouseY, mouseButton);

    }

    public void mouseReleased() {
        locked = false;
    }

}