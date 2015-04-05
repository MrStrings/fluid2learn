package javateste;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task 
 * to execute once 5 seconds have passed.
 */

public class Reminder {
    Timer timer;

    public Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Time's up!");
            timer.cancel(); //Terminate the timer thread
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Reminder reminder = new Reminder(1);
        int i = 0;
        System.out.println("Task scheduled.");
        
    }
}