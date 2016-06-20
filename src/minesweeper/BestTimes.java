package minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		PlayerTime playerTime = new PlayerTime(name, time);

		playerTimes.add(playerTime);

		Collections.sort(playerTimes);
		
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {

		Formatter f = new Formatter();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < playerTimes.size(); i++) {
			PlayerTime tempItem = playerTimes.get(i);
			f.format("\\d*\\. \\w* \\d*\n", i + 1, tempItem.getName(),
					tempItem.getTime());

			f.toString();
			stringBuilder.append(f);
		}

		// while (playerTimes.iterator().hasNext()) {
		// PlayerTime tempItem = playerTimes.iterator().next();
		// f.format("\\d*\\. \\w* \\d*\n", tempItem.getName(),
		// tempItem.getTime());
		// }
		return stringBuilder.toString();

	}

	public void reset() {

		playerTimes.clear();

	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		@Override
		public int compareTo(PlayerTime o) {
			if (this.getTime() < o.getTime()) {
				return -1;
			} else if (this.getTime() == o.getTime()) {
				return 0;
			} else {
				return 1;
			}
		}

	}

}
