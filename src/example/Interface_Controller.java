package example;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;

import java.awt.*;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Interface_Controller {

    //region OBJECTS
    PApplet pApplet;
    ControlP5 cp5;

    Webcam_Window webcam = new Webcam_Window();
    Output_Window output = new Output_Window();
    //endregion

    //region PRIMITIVES
    int id;

    boolean toggle_cam_bt = false;
    boolean toggle_out_bt = false;
    //endregion

    public void init(PApplet pApplet) {

        this.pApplet = pApplet;
        cp5 = new ControlP5(pApplet);

        //region TABS
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
        //endregion

        //region DEFAULT_TAB_CTRL
        cp5.begin(10, 30);

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
        //endregion[s

        //region WEBCAM_TAB_CTRL
        cp5.begin(10, 30);

        cp5.addButton("calibrate_bt")
                .setLabel("CALIBRATE")
                .setValue(0)
                .linebreak()
        ;


        cp5.addToggle("toggle_cam_bt")
                .setLabel("TOGGLE CAMERA")
                .linebreak()
        ;

        cp5.addToggle("toggle_out_bt")
                .setLabel("TOGGLE OUTPUT")
                .linebreak()
        ;

        cp5.end();
        //endregion

        //region SETTINGS_TAB_CTRL
        cp5.begin(10, 30);

        cp5.addToggle("toggle_frameRate_bt")
                .setLabel("FRAME RATE")
                .linebreak()
        ;

        cp5.addToggle("toggle_animation_bt")
                .setLabel("ANIMATION")
                .linebreak()
        ;

        cp5.end();
        //endregion

        //region ARRANGE_TAB_CTRL
        cp5.getController("toggle_cam_bt").moveTo("WEBCAM");
        cp5.getController("toggle_out_bt").moveTo("WEBCAM");
        cp5.getController("calibrate_bt").moveTo("WEBCAM");
        cp5.getController("toggle_frameRate_bt").moveTo("SETTINGS");
        cp5.getController("toggle_animation_bt").moveTo("SETTINGS");
        //endregion

    }

    public void window_handler() {

        if (webcam.isActive == false && toggle_cam_bt == true) {
            webcam.start(pApplet, 320, 240, 100, 100);
        } else if (webcam.isActive == true && toggle_cam_bt == false) {
            webcam.video.stop();
            webcam.isActive = false;
        }

        if (toggle_cam_bt == true) {
            webcam.display(pApplet);
            webcam.draggable(pApplet);
        }

        if (output.isActive == false && toggle_out_bt == true) {
            output.start(pApplet, 320, 240, 100, 100);
            output.isActive = true;

        }

        if (toggle_out_bt == true && webcam.isActive == true) {
            output.display(pApplet, webcam.applyFilters());
            output.draggable(pApplet);

        }
    }

    public void controlEvent(ControlEvent theControlEvent) {
        if (theControlEvent.isTab() && theControlEvent.getTab().isOpen() == true && theControlEvent.getId() == id) {
            theControlEvent.getTab().close();
            theControlEvent.getTab().setColorActive(new Color(0, 45, 90).getRGB());
            id = theControlEvent.getId();
        } else if (theControlEvent.getId() != id && theControlEvent.isTab() || theControlEvent.isTab() && theControlEvent.getTab().isOpen() == false) {
            theControlEvent.getTab().open();
            theControlEvent.getTab().setColorActive(new Color(0, 170, 255).getRGB());
            id = theControlEvent.getId();
        }

        if (theControlEvent.isFrom("calibrate_bt") && webcam.isActive) {
        }

        if (theControlEvent.isFrom("toggle_cam_bt")) {
            toggle_cam_bt = !toggle_cam_bt;
        }

        if (theControlEvent.isFrom("toggle_out_bt")) {
            toggle_out_bt = !toggle_out_bt;
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
