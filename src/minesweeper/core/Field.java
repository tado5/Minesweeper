package minesweeper.core;

import java.util.Random;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {

		if (checkInRange(row, column)) {
			Tile tile = tiles[row][column];
			if (tile.getState() == Tile.State.CLOSED) {
				tile.setState(Tile.State.OPEN);
				if (tile instanceof Mine) {
					state = GameState.FAILED;
					System.out.println("Mine found on position:" + row + " "
							+ column);
					return;
				}
				if (((Clue) tiles[row][column]).getValue() == 0) {
					openAdjacentTiles(row, column);
				}

				if (isSolved()) {
					state = GameState.SOLVED;
					return;
				}
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {

		if (checkInRange(row, column)) {
			Tile tile = tiles[row][column];
			if (tile.getState() == State.CLOSED) {
				getTile(row, column).setState(State.MARKED);
			} else if (tile.getState() == State.MARKED) {
				getTile(row, column).setState(State.CLOSED);

			}
		}

	}

	private boolean checkInRange(int a, int b) {
		if (a < getRowCount() && b < getColumnCount() && a >= 0 && b >= 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random random = new Random();

		for (int i = 0; i < mineCount; i++) {
			int x = random.nextInt(getRowCount() - 1);
			int y = random.nextInt(getColumnCount() - 1);
			if (tiles[x][y] == null) {
				tiles[x][y] = new Mine();
			} else {
				i--;
			}
		}
		fillWithClues();

	}

	private void fillWithClues() {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (getTile(i, j) == null) {
					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {

		int allTiles = getColumnCount() * getRowCount();
		int uncoveredTiles = 0;

		uncoveredTiles = getTilesWithState(State.OPEN);

		if (getMineCount() == (allTiles - uncoveredTiles)) {
			return true;
		} else {
			return false;
		}

	}

	private int getTilesWithState(State state) {
		int numberOfTiles = 0;
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (tiles[i][j].getState() == state) {
					numberOfTiles++;
				}
			}
		}
		return numberOfTiles;
	}

	public int getRemainingMineCount() {

		int numberOfMines = 0;

		numberOfMines = getMineCount() - getTilesWithState(State.MARKED);

		return numberOfMines;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	private void openAdjacentTiles(int row, int col) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = col + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Clue) {
							openTile(actRow, actColumn);

						}
					}
				}
			}
		}
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

	public GameState getGameState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

}
