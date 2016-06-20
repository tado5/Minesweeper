package minesTests;

import static org.junit.Assert.*;
import minesweeper.Settings;
import org.junit.Test;

public class SettingsTest {

	@Test
	public void test() {

		Settings settings = new Settings(15, 15, 10);

		settings.save();

		Settings testSetting;
		testSetting = Settings.load();

		assertEquals(
				"column count error, expected:" + settings.getColumnCount()
						+ ", get: " + testSetting.getColumnCount(),
				settings.getColumnCount(), testSetting.getColumnCount());
		assertEquals("row count error, expected:" + settings.getRowCount()
				+ ", get: " + testSetting.getRowCount(),
				settings.getRowCount(), testSetting.getRowCount());
		assertEquals("mine count error, expected:" + settings.getMineCount()
				+ ", get: " + testSetting.getMineCount(),
				settings.getMineCount(), testSetting.getMineCount());

	}

}
