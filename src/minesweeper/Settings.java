package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int rowCount, columnCount, mineCount;

	public static final Settings BEGINNER = new Settings(9, 9, 10);
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	public static final Settings EXPERT = new Settings(16, 30, 99);

	private static final String SETTING_FILE = System.getProperty("user.home")
			+ System.getProperty("file.separator") + "minesweeper.settings";

	private static FileOutputStream fileOutputStream;
	private static ObjectOutputStream objectOutputStream;

	private static FileInputStream fileInputStream;
	private static ObjectInputStream objectInputStream;

	public Settings(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
	}

	public boolean equals(Settings setting) {

		if (setting.hashCode() == BEGINNER.hashCode()
				|| setting.hashCode() == INTERMEDIATE.hashCode()
				|| setting.hashCode() == EXPERT.hashCode()) {
			return true;
		} else {
			return false;
		}
	}

	public void save() {
		try {
			fileOutputStream = new FileOutputStream(SETTING_FILE);

			objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(this);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("finally")
	public static Settings load() {

		Settings setting = BEGINNER;

		try {
			fileInputStream = new FileInputStream(SETTING_FILE);
			objectInputStream = new ObjectInputStream(fileInputStream);

			setting = (Settings) objectInputStream.readObject();

			objectInputStream.close();
			fileInputStream.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			return setting;
		}
	}

	public int hashCode() {
		return getRowCount() * getColumnCount() * getMineCount();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

}
