package org.petitparser.parser.combinators;

import org.petitparser.context.Context;
import org.petitparser.context.MultiLineStringBuffer;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

import java.util.Objects;

/**
 * A parser that optionally parsers its delegate, or answers nil.
 */
public class OptionalParser extends DelegateParser {

	protected final Object otherwise;

	public OptionalParser(Parser delegate, Object otherwise) {
		super(delegate);
		this.otherwise = otherwise;
	}

	@Override
	public Result parseOn(Context context) {
		Result result = delegate.parseOn(context);
		if (result.isSuccess()) {
			return result;
		} else {
			return context.success(otherwise, context.getPosition(), context.getPosition());
		}
	}

	@Override
	public int fastParseOn(MultiLineStringBuffer buffer, int position) {
		int result = delegate.fastParseOn(buffer, position);
		return result < 0 ? position : result;
	}

	@Override
	protected boolean hasEqualProperties(Parser other) {
		return super.hasEqualProperties(other) && Objects.equals(otherwise, ((OptionalParser) other).otherwise);
	}

	@Override
	public OptionalParser copy() {
		return new OptionalParser(delegate, otherwise);
	}
}
