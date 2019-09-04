package org.petitparser.parser.primitive;

import org.petitparser.context.Context;
import org.petitparser.context.MultiLineStringBuffer;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

/**
 * A parser that consumes nothing and always succeeds.
 */
public class EpsilonParser extends Parser {

	@Override
	public Result parseOn(Context context) {
		return context.success(null, context.getPosition(), context.getPosition());
	}

	@Override
	public int fastParseOn(MultiLineStringBuffer buffer, int position) {
		return position;
	}

	@Override
	public EpsilonParser copy() {
		return new EpsilonParser();
	}
}
