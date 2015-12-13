package example;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import processing.core.PApplet;

import javax.swing.plaf.TableHeaderUI;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class InterfaceHandler implements EventHandler {

    //region OBJECTS

    public static ArrayList<Window> windows = new ArrayList<Window>();

    // Declare PApplet from the processing core.
    PApplet pApplet;
    // Declare ControlP5 interface.
    ControlP5 controlP5;


    //endregion

    //region PRIMITIVES
    // Declare integer 'id' to be used for registering the last active tab.
    int id;

    // Boolean used to check whether or not the frame rate should be drawn.
    boolean toggle_frameRate_btn;

    //endregion

    /**
     * This class is responsible for handling interface elements in the program such as ControlP5 controllers and the
     * a Window class which also contains GUI elements of its own.
     *
     * @param pApplet
     * @see Window
     */

    InterfaceHandler(PApplet pApplet) {

        this.pApplet = pApplet;

        controlP5 = new ControlP5(pApplet);

        //region TABS
        controlP5.getDefaultTab().setTitle("FILE");

        controlP5.addTab("TOOLS");
        controlP5.addTab("SETTINGS");

        controlP5.getDefaultTab()
                .activateEvent(true)
                .setId(0)
        ;

        controlP5.getTab("TOOLS")
                .activateEvent(true)
                .setId(1)
        ;

        controlP5.getTab("SETTINGS")
                .activateEvent(true)
                .setId(2)
        ;
        //endregion

        //region DEFAULT_TAB_CTRL
        controlP5.begin(10, 30);

        controlP5.addButton("save_bt")
                .setLabel("SAVE")
                .linebreak()
        ;

        controlP5.addButton("load_bt")
                .setLabel("LOAD")
                .linebreak()
        ;

        controlP5.addButton("default_bt")
                .setLabel("DEFAULT")
                .linebreak()
        ;

        controlP5.addButton("exit_bt")
                .setLabel("EXIT")
                .linebreak()
        ;

        controlP5.end();
        //endregion

        //region TOOLS_TAB_CTRL
        controlP5.begin(10, 30);

        controlP5.addButton("add_cam_btn")
                .setLabel("ADD CAMERA")
                .linebreak()
        ;

        controlP5.addButton("add_tracker_btn")
                .setLabel("ADD TRACKER")
                .linebreak()
        ;

        controlP5.end();
        //endregion

        //region SETTINGS_TAB_CTRL
        controlP5.begin(10, 30);

        controlP5.addToggle("toggle_frameRate_btn")
                .setLabel("FRAME RATE")
                .linebreak()
        ;

        controlP5.end();
        //endregion

        //region ARRANGE_CTRL
        controlP5.getController("add_cam_btn").moveTo("TOOLS");
        controlP5.getController("add_tracker_btn").moveTo("TOOLS");

        controlP5.getController("toggle_frameRate_btn").moveTo("SETTINGS");

        //endregion
    }

    public void update() {

        for (Window win : windows) {
            win.update();
            win.cp5.draw();
        }

        if (toggle_frameRate_btn) {
            pApplet.pushStyle();
            pApplet.fill(255);
            pApplet.text("FPS: " + pApplet.frameRate, 105, 10);
            pApplet.popStyle();
        }
    }

    @Override
    public void mousePressed() {

    }

    @Override
    public void mouseDragged() {

    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void mouseClicked() {

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

        if (theControlEvent.isFrom("add_cam_btn")) {
            windows.add(new Window_Cam(pApplet, 320, 240, pApplet.width / 2, pApplet.height / 2));
        }

        if (theControlEvent.isFrom("add_tracker_btn")) {
            windows.add(new Window_Tracker(pApplet, 320, 240));
//            Thread t = new Thread(new PSMove_Sensor_Handler());
//            t.start();
        }

        if (theControlEvent.isFrom("toggle_frameRate_btn")) {
            toggle_frameRate_btn = !toggle_frameRate_btn;
        }
    }

    public Control_Frame addControlFrame(String theName, int theWidth, int theHeight) {
        Frame f = new Frame(theName);
        Control_Frame p = new Control_Frame(this, theWidth, theHeight);
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
