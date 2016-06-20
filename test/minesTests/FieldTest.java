package minesTests;

import static org.junit.Assert.*;

import java.util.Random;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;

import org.junit.Test;

public class FieldTest {

	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;

	@Test
	public void testGenerate() {

		Random rndSeed = new Random();

		int rows = rndSeed.nextInt(26) + 1;
		int columns = rndSeed.nextInt(26) + 1;
		int expectedMines = rndSeed.nextInt(50) + 1;

		// Field field = new Field(rows, columns, expectedMines);

		Field field = new Field(ROWS, COLUMNS, MINES);

		int minesFromField = 0;
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				if (field.getTile(i, j) instanceof Mine) {
					minesFromField++;
				}
			}
		}

		assertTrue("Mines generated in field, does not match with given data.",
				MINES == minesFromField);

		assertEquals(ROWS, field.getRowCount());
		assertEquals(COLUMNS, field.getColumnCount());

	}

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getGameState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field
						.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getGameState());
				} else {
					assertNotSame(GameState.FAILED, field.getGameState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getGameState());
	}

}
