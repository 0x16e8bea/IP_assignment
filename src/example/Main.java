package example;

import gab.opencv.OpenCV;
import processing.core.*;
import processing.video.*;
import controlP5.*;

public class Main extends PApplet {

    Interface_Controller _interface = new Interface_Controller();

    public void setup() {
        size(displayWidth, displayHeight);
        noFill();
        noStroke();

        // ControlP5
        _interface.init(this);
    }

    public void draw() {
        background(0, 10, 30);
        _interface.window_handler();
    }

    public void controlEvent(ControlEvent theControlEvent) {
        _interface.controlEvent(theControlEvent);
    }

    public void captureEvent(Capture c) {
        c.read();
    }

    // Should be optimized
    public void mousePressed() {
        _interface.webcam.mousePressed(mouseX, mouseY);
        _interface.output.mousePressed(mouseX, mouseY);
        _interface.webcam.histogram.mousePressed(mouseX, mouseY);
    }

    // Should be optimized
    public void mouseDragged() {
        _interface.webcam.mouseDragged(mouseX, mouseY);
        _interface.output.mouseDragged(mouseX, mouseY);
        _interface.webcam.histogram.mouseDragged(mouseX, mouseY);
    }

    public void mouseReleased() {
        _interface.webcam.mouseReleased();
        _interface.output.mouseReleased();
        _interface.webcam.histogram.mouseReleased();
    }
}