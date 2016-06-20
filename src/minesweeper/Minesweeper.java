package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;

	private static Minesweeper minesweeper;

	private Settings setting;

	private long startMillis;

	private static BestTimes bestTimes = new BestTimes();

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		minesweeper = this;

		setting = Settings.load();
		userInterface = new ConsoleUI();

		startMillis = System.currentTimeMillis();
		Field field = new Field(9, 9, 10);
		userInterface.newGameStarted(field);
	}

	public static Minesweeper getInstance() {
		return minesweeper;
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		new Minesweeper();
	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		setting.save();
		this.setting = setting;
	}
}
