package example;

import java.awt.Frame;
import java.awt.BorderLayout;

import controlP5.ControlP5;
import controlP5.Tab;
import processing.core.PApplet;
import processing.video.Capture;

import java.awt.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Interface_Controller {

    Tab tab;
    Webcam_Handler webcam = new Webcam_Handler();
    ControlP5 cp5;

    public void init(PApplet pApplet) {

        cp5 = new ControlP5(pApplet);

        // Add interface components
        tab = cp5.addTab("WEBCAM");

        // Add buttons to 'default'
        cp5.addButton("save_bt")
                .setLabel("SAVE")
                .setValue(0)
                .linebreak()
        ;

        cp5.addButton("load_bt")
                .setLabel("LOAD")
                .setValue(0)
                .linebreak()
        ;

        cp5.addButton("default_bt")
                .setLabel("DEFAULT")
                .setValue(0)
                .linebreak()
        ;

        cp5.addToggle("toggle_cam_bt");

        cp5.getController("toggle_cam_bt").moveTo("WEBCAM");

    }

    public void cam_handler(PApplet pApplet) {

        if (webcam.isActive == false && Main.toggle_cam_bt == true) {
            webcam.start(pApplet, 320, 240, 100, 100);
        } else if (webcam.isActive == true && Main.toggle_cam_bt == false) {
            webcam.video.stop();
            webcam.isActive = false;
        }

        if (Main.toggle_cam_bt == true) {
            webcam.display(pApplet);
            webcam.draggable(pApplet);
        }

    }

    public ControlFrame addControlFrame(String theName, int theWidth, int theHeight) {
        Frame f = new Frame(theName);
        ControlFrame p = new ControlFrame(this, theWidth, theHeight);
        f.add(p);
        p.init();
        f.setTitle(theName);
        f.setSize(p.w, p.h);
        f.setLocation(100, 100);
        f.setResizable(false);
        f.setVisible(true);
        return p;
    }
}
