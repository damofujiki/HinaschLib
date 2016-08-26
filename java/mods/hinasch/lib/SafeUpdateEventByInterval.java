package mods.hinasch.lib;

public abstract class SafeUpdateEventByInterval extends SafeUpdateEvent{


	int interval = 0;

	@Override
	public void loop() {
		interval ++;
		if(interval>this.getIntervalThresold()){
			interval = 0;
			this.loopByInterval();
		}
	}

	public abstract int getIntervalThresold();
	public abstract void loopByInterval();
}
