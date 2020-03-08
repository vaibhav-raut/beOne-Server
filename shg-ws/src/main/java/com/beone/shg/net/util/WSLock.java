package com.beone.shg.net.util;


public class WSLock implements OTimerTaskI {
    private boolean locked = false;
    private OTimer timer = new OTimer();
    
    public WSLock() {
		super();
	}

	public boolean isLocked() {
		return locked;
	}

	public void unLock() {
		locked = false;
		timer.clear();
	}

	public boolean lock(int min) {
		if(min <= 0) {
			return false;
		}
		locked = true;
		timer.schedule(this, min * 60);
		return locked;
	};

	@Override
	public void task() {
		locked = false;
	}

	public int getSecondsToCount() {
		return timer.getSecondsToCount();
	}

	public int getSecondsElapsed() {
		return timer.getSecondsElapsed();
	}

	public int getSecondsRemaining() {
		return timer.getSecondsRemaining();
	}
}
