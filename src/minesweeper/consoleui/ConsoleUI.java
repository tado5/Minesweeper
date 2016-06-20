package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.Mine;
import minesweeper.core.Tile;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field
	 * )
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();

			switch (field.getGameState()) {
			case SOLVED:
				System.out.println("Game solved");
				update();
				System.exit(0);
				break;
			case FAILED:
				System.out.println("Game OVER");
				update();
				System.exit(0);
				break;
			default:
				break;

			}
		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		char row = 'A';
		int col = 1;

		System.out.println("Time: " + Minesweeper.getInstance().getPlayingSeconds());
		System.out.println("Mines remaining: " + field.getRemainingMineCount());
		for (int i = -1; i < field.getRowCount(); i++) {
			for (int j = -1; j < field.getColumnCount(); j++) {
				if (i < 0 && j < 0) {
					System.out.printf("  ");
				} else if (i < 0) {
					if (i < 10) {
						System.out.printf(col + " ");
						col++;
					} else {
						System.out.println(col);
						col++;
					}
				} else if (j < 0) {
					System.out.printf(row + " ");
					row++;
				} else {

					Tile tempTile = field.getTile(i, j);
					switch (tempTile.getState()) {
					case CLOSED:
						System.out.printf("- ");
						break;
					case OPEN:
						if (tempTile instanceof Mine) {
							System.out.printf("X ");
						} else if (tempTile instanceof Clue) {
							System.out.printf(((Clue) tempTile).getValue() + " ");
						}
						break;
					case MARKED:
						System.out.printf("M ");
						break;
					}
				}
			}
			System.out.println();
		}

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {

		try {
			handleInput(readLine());
		} catch (WrongFormatException e) {
			e.printStackTrace();
		}

	}

	private void handleInput(String input) throws WrongFormatException {

		Pattern pattern = Pattern.compile("([oOmMxX]{1})([a-zA-Z]{1})?(\\d{1,2})?");

		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {

			String command = matcher.group(1).toUpperCase();
			int row, col;

			switch (command) {
			case "X":
				// System.out.println(command);
				System.exit(0);
				break;
			case "O":
				row = matcher.group(2).toLowerCase().toCharArray()[0] - 'a';
				col = Integer.parseInt(matcher.group(3)) - 1;

				// System.out.println(command + " " + row + " " + col);
				field.openTile(row, col);
				break;
			case "M":
				row = matcher.group(2).toLowerCase().toCharArray()[0] - 'a';
				col = Integer.parseInt(matcher.group(3)) - 1;

				// System.out.println(command + " " + row + " " + col);
				field.markTile(row, col);
				break;

			}

		}

	}
}
