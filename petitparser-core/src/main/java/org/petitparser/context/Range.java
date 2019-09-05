package org.petitparser.context;

public class Range {
	public static final class Location {
		public final int line;
		public final int position;

		public Location(int line, int position) {
			this.line = line;
			this.position = position;
		}
	}

	private final Location from;
	private final Location to;

	public Range(int lineFrom, int positionFrom, int lineTo, int positionTo) {
		this(new Location(lineFrom, positionFrom), new Location(lineTo, positionTo));
	}

	public Range(Location from, Location to) {
		this.from = from;
		this.to = to;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}
}
