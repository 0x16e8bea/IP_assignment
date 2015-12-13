package example;

import controlP5.ControlEvent;

/**
 * Created by Mikkel Mogensen on 12/11/2015.
 */
public interface EventHandler {

    void mousePressed();

    void mouseDragged();

    void mouseReleased();

    void mouseClicked();

    void controlEvent(ControlEvent theControlEvent);
}
