package UI;

import java.util.Timer;
import java.util.TimerTask;

public class CountDown {
    Timer timer;
    public static boolean finished = false;

    public CountDown(int seconds) {
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
