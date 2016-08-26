package mods.hinasch.lib;


public abstract class SafeUpdateEvent {

	/**
	 * ここでtrue返るとイベントが終了する。
	 * @return
	 */
	public abstract boolean hasFinished();
	/**
	 * メインループはここに書く
	 */
	public abstract void loop();
}
