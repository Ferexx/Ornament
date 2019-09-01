package UI;

import java.util.Timer;
import java.util.TimerTask;

public class Notification {
    Timer timer;
    public static boolean finished = false;

    public Notification(int seconds) {
        finished = false;
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            finished = true;
            timer.cancel(); //Terminate the timer thread
        }
    }
}
