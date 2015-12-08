package example;

import java.awt.*;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Interface_Controller {

    //region OBJECTS
    PApplet pApplet;
    ControlP5 cp5;

    Webcam_Window webcam;
    Output_Window output = new Output_Window();

    //endregion

    //region PRIMITIVES
    int id;

    boolean toggle_frameRate_bt = false;


    //endregion

    public void init(PApplet pApplet) {

        this.pApplet = pApplet;
        cp5 = new ControlP5(pApplet);

        webcam = new Webcam_Window(pApplet, cp5, 320, 240, 100, 100);

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

        cp5.addButton("start_btn")
                .setLabel("START WEBCAM")
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
        cp5.getController("start_btn").moveTo("WEBCAM");

        cp5.getController("toggle_frameRate_bt").moveTo("SETTINGS");
        cp5.getController("toggle_animation_bt").moveTo("SETTINGS");

        //endregion
    }

    public void window_handler() {

        webcam.display();

        if (toggle_frameRate_bt) {
            pApplet.text(pApplet.frameRate, 0, pApplet.displayWidth - 10);
        }
    }

    public void controlEvent(ControlEvent theControlEvent) {
        /**
         * Calls close on a specific tab if it is open and clicked on by the mouse. Does the opposite if the tab is
         * not currently active.
         */

        if (theControlEvent.isTab() && theControlEvent.getTab().isOpen() == true && theControlEvent.getId() == id) {
            theControlEvent.getTab().close();
            theControlEvent.getTab().setColorActive(new Color(0, 45, 90).getRGB());
            id = theControlEvent.getId();
        } else if (theControlEvent.getId() != id && theControlEvent.isTab() || theControlEvent.isTab() && theControlEvent.getTab().isOpen() == false) {
            theControlEvent.getTab().open();
            theControlEvent.getTab().setColorActive(new Color(0, 170, 255).getRGB());
            id = theControlEvent.getId();
        }

        if (theControlEvent.isFrom("start_btn")) {
            webcam.isActive = true;
        }

        if (theControlEvent.isFrom("x_btn")) {
            webcam.isActive = false;
        }

        if (theControlEvent.isFrom("toggle_frameRate_btn")) {
            toggle_frameRate_bt = !toggle_frameRate_bt;
        }

        if (theControlEvent.isFrom("calibrate_btn")) {
            webcam.histogram.isActive = true;
        }

        if (theControlEvent.isFrom("hsb_range")) {
            // min and max values are stored in an array.
            // access this array with controller().arrayValue().
            // min is at index 0, max is at index 1.
            webcam.histogram.range_min = (int)(theControlEvent.getController().getArrayValue(0));
            webcam.histogram.range_max = (int)(theControlEvent.getController().getArrayValue(1));
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
