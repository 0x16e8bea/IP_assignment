package example;

import processing.core.*;
import processing.video.*;
import controlP5.*;

import java.util.concurrent.ExecutionException;

public class Main extends PApplet {

    //static boolean locked = false;

    Interface_Controller _interface = new Interface_Controller();

    public void setup() {
        size(800, 600);
        noFill();

        // ControlP5
        _interface.init(this);

    }

    public void draw() {
        background(0, 10, 30);

        pushMatrix();
        scale(2);
        popMatrix();

        try {
            _interface.window_handler();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void controlEvent(ControlEvent theControlEvent) throws ExecutionException, InterruptedException {

        _interface.controlEvent(theControlEvent);
    }

    public void captureEvent(Capture c) {
        c.read();
    }

    // Should be optimized
    public void mousePressed() {
        _interface.webcam.mousePressed(mouseX, mouseY);
        _interface.output.mousePressed(mouseX, mouseY);
    }

    // Should be optimized
    public void mouseDragged() {
        _interface.webcam.mouseDragged(mouseX, mouseY);
        _interface.output.mouseDragged(mouseX, mouseY);

    }

    public void mouseReleased() {
        _interface.webcam.mouseReleased();
        _interface.output.mouseReleased();

        //locked = false;
    }

}