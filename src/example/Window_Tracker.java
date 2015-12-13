package example;

import controlP5.ColorWheel;
import controlP5.Textlabel;
import processing.core.PApplet;

import java.time.temporal.TemporalAccessor;

/**
 * Created by Mikkel Mogensen on 12-Dec-15.
 */
public class Window_Tracker extends Window {

    PSMove_Sensor_Handler psMoveHandler;

    Window_Tracker(PApplet pApplet, int x, int y) {

        super(pApplet);

        this.bX = x;
        this.bY = y;

        c = cp5.addColorWheel("color_wheel").setLabel("");

        cp5.begin(0, 0);

        addGroup("SHOW DATA");

        angle = cp5.addTextlabel("angle");
        xVec = cp5.addTextlabel("xVec");
        yVec = cp5.addTextlabel("yVec");
        zVec = cp5.addTextlabel("zVec");

        cp5.end();

        this.bW = c.getWidth();
        this.bH = c.getHeight();

        psMoveHandler = new PSMove_Sensor_Handler();

        Thread t = new Thread(psMoveHandler);
        t.start();

        cp5.getController("bar").setLabel("TRACKER_WINDOW_ID: " + id);

    }

    ColorWheel c;
    Textlabel angle, xVec, yVec, zVec;

    @Override
    public void update() {
        super.update();

        c.setPosition(bX, bY);

        psMoveHandler.r = c.r();
        psMoveHandler.g = c.g();
        psMoveHandler.b = c.b();

        cp5.begin(bX, bY + c.getWidth() + 15);
        angle.setText("Angle: " + psMoveHandler.angle).setPosition(bX, bY + c.getWidth() + 15);
        xVec.setText("xVec: " + psMoveHandler.xVec).setPosition(bX, bY + c.getWidth() + 25);
        yVec.setText("yVec: " + psMoveHandler.yVec).setPosition(bX, bY + c.getWidth() + 35);
        zVec.setText("zVec: " + psMoveHandler.zVec).setPosition(bX, bY + c.getWidth() + 45);

        cp5.end();


        pApplet.pushMatrix();
        pApplet.pushStyle();

        pApplet.stroke(255);
        pApplet.translate(pApplet.width / 2, pApplet.height / 2);

        pApplet.rotate(psMoveHandler.angle, -psMoveHandler.xVec, psMoveHandler.yVec, -psMoveHandler.zVec);
        pApplet.box(100);

        pApplet.popStyle();
        pApplet.popMatrix();

    }
}
