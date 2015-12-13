package example;

import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by Mikkel on 15-Nov-15.
 */
// the ControlFrame class extends PApplet, so we
// are creating a new processing applet inside a
// new frame with a controlP5 object loaded
public class Control_Frame extends PApplet {

    int w, h;
    int abc = 100;


    ControlP5 cp5;
    Object parent;

    public Control_Frame(Object theParent, int theWidth, int theHeight) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }

    public void setup() {
        size(w, h);
        frameRate(25);
        cp5 = new ControlP5(this);
        cp5.addSlider("abc").setRange(0, 255).setPosition(10, 10);
        cp5.addSlider("def").plugTo(parent, "def").setRange(0, 255).setPosition(10, 30);
    }

    public void draw() {
        background(abc);
    }

    public ControlP5 control() {
        return cp5;
    }
}
