package org.petitparser.context;

public class MultiLineStringBuffer {

	private final String buffer;
	private int[] lengths;

	public MultiLineStringBuffer(String buffer) {
		this.buffer = buffer;
		String[] splits = this.buffer.split("\n");
		this.lengths = new int[splits.length];
		for (int x = 0; x < splits.length; x++) {
			this.lengths[x] = splits[x].length() + 1;
		}
	}

	public int length() {
		return buffer.length();
	}

	public char charAt(int position) {
		return buffer.charAt(position);
	}

	public String substring(int start, int stop) {
		return buffer.substring(start, stop);
	}

	public String getBuffer() {
		return buffer;
	}

	public Range getRange(int from, int to) {
		int lineFrom = 0;
		int lineTo = 0;
		int positionFrom = 0;
		int positionTo = 0;
		for (int x = 0; x < this.lengths.length; x++) {
			int length = this.lengths[x];
			if (from > length) {
				from -= length;
			} else {
				lineFrom = x + 1;
				positionFrom = from;
				break;
			}
		}
		for (int x = 0; x < this.lengths.length; x++) {
			int length = this.lengths[x];
			if (to > length) {
				to -= length;
			} else {
				lineTo = x + 1;
				positionTo = to;
				break;
			}
		}

		return new Range(lineFrom, positionFrom, lineTo, positionTo);
	}

}
