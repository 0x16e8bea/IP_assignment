package example;

import controlP5.ControlEvent;
import processing.core.PApplet;
import processing.video.Capture;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends PApplet {

    InterfaceHandler _interface;
    public static ArrayList<EventHandler> eventListeners = new ArrayList<EventHandler>();

    public static void main(String args[]) {
        PApplet.main(new String[]{"--present", "Main"});
    }

    public void setup() {
        // Setup window to the size of the screen
        size(displayWidth, displayHeight, P3D);
        noFill();
        noStroke();


        // ControlP5
        _interface = new InterfaceHandler(this);
        eventListeners.add(_interface);
    }

    public void draw() {
        background(0, 10, 30);
        _interface.update();
    }

    public void captureEvent(Capture c) {
        c.read();
    }

    public static Iterator<EventHandler> it;

    public static List<EventHandler> thingsToAdd = new ArrayList<EventHandler>();
    public static List<EventHandler> thingsToRemove = new ArrayList<EventHandler>();

    public void controlEvent(ControlEvent theControlEvent) {
        for (EventHandler ec : eventListeners) {
            ec.controlEvent(theControlEvent);
        }
        eventListeners.addAll(thingsToAdd);
        eventListeners.removeAll(thingsToRemove);
        thingsToAdd.clear();
        thingsToRemove.clear();
    }

    public void mouseClicked() {
        for (EventHandler ec : eventListeners) {
            ec.mouseClicked();
        }
    }

    public void mouseReleased() {
        for (EventHandler ec : eventListeners) {
            ec.mouseReleased();
        }
    }

    public void mousePressed() {
        for (EventHandler ec: eventListeners) {
            ec.mousePressed();
        }
      }

    public void mouseDragged() {
        for (EventHandler ec: eventListeners) {
            ec.mouseDragged();
        }
        eventListeners.addAll(thingsToAdd);
        eventListeners.removeAll(thingsToRemove);
        thingsToAdd.clear();
        thingsToRemove.clear();
    }
}