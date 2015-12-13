package example;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Group;
import processing.core.PApplet;

/**
 * Created bY Mikkel on 16-Nov-15.
 */


public class Window implements EventHandler {

    public boolean isActive = false;
    public ControlP5 cp5;
    public int id;
    protected int bX, bY, bW, bH;
    protected int iw = 25, ih = 15;
    protected int xCenter, yCenter;
    protected PApplet pApplet;
    protected boolean showBar = true;
    Group WIN_GROUP;

    Window(PApplet pApplet) {

        this.pApplet = pApplet;
        cp5 = new ControlP5(pApplet);
        cp5.setAutoDraw(false);

        System.out.println("id: " + InterfaceHandler.windows.size());
        this.id = InterfaceHandler.windows.size();

        if (showBar) {
            initBar();
        }
        cp5.hide();

        Main.thingsToAdd.add(this);

        isActive = true;
    }

    public void initBar() {
        cp5.addButton("bar");
        cp5.addButton("exit_btn_" + id)
                .setSize(iw, ih)
                .setLabel("X");

        cp5.getController("exit_btn_" + id).moveTo("global");
    }

    public void update() {

        // Update position and size of title bar
        if (showBar) {
            cp5.getController("bar").setPosition(bX, bY - ih);
            cp5.getController("bar").setSize(bW - iw, ih);
            cp5.getController("exit_btn_" + id).setPosition(bX + bW - cp5.getController("exit_btn_" + id).getWidth(), bY - cp5.getController("exit_btn_" + id).getHeight());
        }

        if (isActive) {
            draw();

            if (WIN_GROUP != null)
                WIN_GROUP.setPosition(bX, bY + bH + WIN_GROUP.getHeight() + 1);

            if (!cp5.isVisible()) {
                cp5.show();
            }
        } else if (cp5.isVisible() && !isActive) {
            cp5.hide();
        }
    }

    protected Window addGroup(String name) {
        WIN_GROUP = new Group(cp5, name);
        WIN_GROUP.setSize(100, 10);
        WIN_GROUP.moveTo("global");
        WIN_GROUP.close();
        return this;
    }

    protected Window addButton(String name) {
        cp5.addButton(name)
                .setGroup(WIN_GROUP)
                .setSize(100, 10)
                .getCaptionLabel().align(cp5.LEFT, cp5.CENTER);

        return this;
    }

    protected void draw() {

    }

    @Override
    public void mouseClicked() {

    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void mousePressed() {
        xCenter = pApplet.mouseX - (bX + bW / 2);
        yCenter = pApplet.mouseY - (bY + bH / 2);
    }

    public void mouseDragged() {
        if (showBar) {
            if (cp5.getController("bar").isActive() && cp5.getController("bar").isMouseOver()) {
                this.bX = pApplet.mouseX - xCenter - bW / 2;
                this.bY = pApplet.mouseY - yCenter - bH / 2;
            }
        }
    }

    @Override
    public void controlEvent(ControlEvent theControlEvent) {

        if (theControlEvent.isFrom("exit_btn_" + id)) {
            System.out.println("exit");
            this.isActive = false;
            InterfaceHandler.windows.remove(this);
            Main.thingsToRemove.add(this);
            cp5.dispose();
        }
    }
}