package example;

import java.awt.*;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Interface_Controller {

    Webcam_Window webcam = new Webcam_Window();
    Output_Window output = new Output_Window();

    ControlP5 cp5;

    public void init(PApplet pApplet) {

        cp5 = new ControlP5(pApplet);

        cp5.begin(10,30);
        // Add interface components
        cp5.getDefaultTab().setTitle("FILE");
        cp5.addTab("WEBCAM");
        cp5.addTab("SETTINGS");

        cp5.getDefaultTab()
                .activateEvent(true)
                .setId(0)
        ;

        cp5.getTab("WEBCAM")
                .activateEvent(true)
                .setId(1)
        ;

        cp5.getTab("SETTINGS")
                .activateEvent(true)
                .setId(2)
        ;


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

        cp5.addButton("exit_bt")
                .setLabel("EXIT")
                .setValue(0)
                .linebreak()
        ;

        cp5.end();

        cp5.begin(10,30);

        cp5.addButton("calibrate_bt")
                .setLabel("CALIBRATE")
                .setValue(0)
                .linebreak()
        ;


        cp5.addToggle("toggle_cam_bt")
            .setLabel("TOGGLE CAMERA")
        ;

        cp5.addToggle("toggle_out_bt")
                .setLabel("TOGGLE OUTPUT")
        ;

        cp5.end();

        cp5.getController("toggle_cam_bt").moveTo("WEBCAM");
        cp5.getController("toggle_out_bt").moveTo("WEBCAM");
        cp5.getController("calibrate_bt").moveTo("WEBCAM");

    }


    public void window_handler(PApplet pApplet) {

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

        if (output.isActive == false && Main.toggle_out_bt == true) {
            output.start(pApplet, 320, 240, 100, 100);
            output.isActive = true;
        }

        if (Main.toggle_out_bt == true && webcam.isActive == true) {
            output.display(pApplet, webcam.applyNormalize());
            output.draggable(pApplet);
        }

    }

    int id;

    // Maybe this can be optimized ... but for now it works
    public void controlEvent(ControlEvent theControlEvent) {
        if (theControlEvent.isTab() && theControlEvent.getTab().isOpen() == true && theControlEvent.getId() == id) {
            theControlEvent.getTab().close();
            theControlEvent.getTab().setColorActive(new Color(0,45,90).getRGB());
            id = theControlEvent.getId();
        } else if (theControlEvent.getId() != id && theControlEvent.isTab() || theControlEvent.isTab() && theControlEvent.getTab().isOpen() == false) {
            theControlEvent.getTab().open();
            theControlEvent.getTab().setColorActive(new Color(0,170,255).getRGB());
            id = theControlEvent.getId();
        }

        if (theControlEvent.isFrom("calibrate_bt") && webcam.isActive) {
            webcam.applyNormalize();
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
