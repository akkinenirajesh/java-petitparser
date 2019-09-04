package org.petitparser.context;

/**
 * An immutable parse success.
 */
public class Success extends Result {

	private final Object result;
	private final int positionStart;

	public Success(MultiLineStringBuffer buffer, int positionStart, int positionEnd, Object result) {
		super(buffer, positionEnd);
		this.positionStart = positionStart;
		this.result = result;
	}

	@Override
	public boolean isSuccess() {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get() {
		return (T) result;
	}

	@Override
	public String toString() {
		return super.toString() + ": " + result;
	}

	public int getPositionStart() {
		return positionStart;
	}

	@Override
	public Range getRange() {
		return buffer.getRange(positionStart, this.position);
	}

}
