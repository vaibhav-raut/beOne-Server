package com.beone.shg.net.util;

import java.util.Timer;
import java.util.TimerTask;

public class OTimer {
    private Timer timer = null;
    private OTask oTask = null;
    
    public OTimer() {
		super();
	}

    public void clear() {
    	if(timer != null) {
    		timer.cancel();
    		timer = null;
    		oTask = null;
    	}
    }

	public int getSecondsToCount() {
		if(oTask == null) {
			return -1;
		}
		return oTask.getSecondsToCount();
	}

	public int getSecondsElapsed() {
		if(oTask == null) {
			return -1;
		}
		return oTask.getSecondsElapsed();
	}

	public int getSecondsRemaining() {
		if(oTask == null) {
			return -1;
		}
		return (oTask.getSecondsToCount() - oTask.getSecondsElapsed());
	}

	public void schedule(OTimerTaskI task, int sec) {
		if(sec <= 0) {
			return;
		}
		if(timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		oTask = new OTask(task, sec);
		
		timer.schedule(oTask, 1000, 1000);
	}

	class OTask extends TimerTask {

		private OTimerTaskI task;
		private int secondsToCount;
		private int secondsElapsed;
		
		public OTask(OTimerTaskI task, int secondsToCount) {
			super();
			this.task = task;
			this.secondsToCount = secondsToCount;
			this.secondsElapsed = 0;
		}

		@Override
		public void run() {
			if(secondsElapsed >= secondsToCount) {
				task.task();
			}
			else {
				secondsElapsed++;
			}
		}

		public int getSecondsToCount() {
			return secondsToCount;
		}

		public int getSecondsElapsed() {
			return secondsElapsed;
		}
	}
}
