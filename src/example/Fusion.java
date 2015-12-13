package example;

import com.jogamp.opengl.math.Quaternion;
import io.thp.psmove.*;
import org.opencv.video.KalmanFilter;

/**
 * Created by Mikkel Mogensen on 12-Dec-15.
 */
public class Fusion implements Runnable {

    private PSMoveTracker tracker;
    private PSMoveFusion fusion;
    private PSMove move;

    Fusion() {
        initPSMove();
    }

    private void initPSMove() {

        psmoveapi.init(PSMove_Version.PSMOVE_CURRENT_VERSION);

        if (psmoveapi.count_connected() == 0) {
            System.out.println("Please connect a controller first.");
            System.exit(1);
        }

        tracker = new PSMoveTracker();
        tracker.set_mirror(1);
        _PSMoveTrackerSmoothingSettings settings = new _PSMoveTrackerSmoothingSettings();
        settings.setFilter_3d_type(PSMoveTracker_Smoothing_Type.Smoothing_Kalman);

        fusion = new PSMoveFusion(tracker, 1.f, 1000.f);
        move = new PSMove();
        move.enable_orientation(1);
        move.reset_orientation();

        while (tracker.enable(move) != Status.Tracker_CALIBRATED);

    }

    private void loadMatrix(PSMoveMatrix4x4 matrix) {

    }

    private void updatePSMove() {
        while (move.poll() != 0) {}

        tracker.update_image();
        tracker.update();

        float[] x = {0.f}, y = {0.f}, z = {0.f};

        fusion.get_position(move, x, y, z);

//        System.out.println(x[0]);

        if ((move.get_buttons() & Button.Btn_MOVE.swigValue()) != 0) {
            loadMatrix(fusion.get_modelview_matrix(move));
            move.reset_orientation();

        }
    }

    @Override
    public void run() {
        while (true) {
            updatePSMove();
        }
    }
}
