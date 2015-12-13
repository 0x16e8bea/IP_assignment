package example;

import controlP5.ControlEvent;
import controlP5.ScrollableList;
import gab.opencv.OpenCV;
import processing.core.PApplet;
import processing.core.PVector;
import processing.video.Capture;

/**
 * Created by Mikkel on 15-Nov-15.
 */
public class Window_Cam extends Window {

    Capture video;
    OpenCV openCV;

    Window_Hist histogram;
    ScrollableList dd;
    String[] cameras;

    /**
     * This class is responsible for showing the camera feed and take appropriate actions when the user specifies
     * certain parameters in the GUI such as when setting the threshold range. OpenCV is used in this class to
     * do most image-processing operations, however a custom class called {@code Image_Processing} capable of
     * point-processing exists as well.
     *
     * @param pApplet used bY processing
     * @param vW      width of the window
     * @param vH      height of the window
     * @param x       x-coordinate of the window
     * @param y       y-coordinate of the window
     * @see ImageProcessing
     */
    Window_Cam(PApplet pApplet, int vW, int vH, int x, int y) {

        super(pApplet);

        this.bX = x;
        this.bY = y;
        this.bW = vW;
        this.bH = vH;

        cameras = Capture.list();

        //region CAM_WIN_GROUP
        cp5.begin(0, 0);

        addGroup("OPTIONS")
                .addButton("CALIBRATE_" + id);

        dd = cp5.addScrollableList("SWITCH CAMERA")
                .setPosition(0, 10)
                .close()
                .setGroup("OPTIONS");

        for (int i = 0; i < cameras.length; i++) {
            if (cameras[i].length() > 15) {
                dd.addItem(cameras[i].substring(5, 20) + " [...] ", i);
            } else {
                dd.addItem(cameras[i].substring(5, cameras[i].length()), i);
            }
        }
        cp5.getController("bar").setLabel("CAM_WINDOW_ID: " + id);
        cp5.end();
        //endregion
    }

/*    public int[] applyFilters() {

        processImg.init(video.pixels).normalize();

        return processImg.out;
    }*/

    public void update() {
        super.update();
    }

    protected void draw() {
        pApplet.fill(0);
        pApplet.rect(bX, bY, bW, bH);

        if (video != null) {
            pApplet.image(video, bX, bY);
        }
    }

    public Window_Cam debugBrightestPoint() {
        PVector loc = openCV.max();
        pApplet.pushStyle();
        pApplet.stroke(255, 0, 0);
        pApplet.strokeWeight(4);
        pApplet.noFill();
        pApplet.ellipse(bX + loc.x, bY + loc.y, 10, 10);
        pApplet.popStyle();

        return this;
    }

    @Override
    public void controlEvent(ControlEvent theControlEvent) {

        super.controlEvent(theControlEvent);

        if (theControlEvent.isFrom("CALIBRATE_" + id)) {
            InterfaceHandler.windows.add(new Window_Hist(pApplet, video.pixels, 320, 240, 100, 100));
        }

        if (theControlEvent.isFrom(dd)) {

            if (video != null)
                video.stop();

            video = new Capture(pApplet, 320, 240);
            video.start();
        }
    }
}
