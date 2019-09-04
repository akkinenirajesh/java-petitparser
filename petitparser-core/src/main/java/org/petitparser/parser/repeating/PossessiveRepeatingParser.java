package org.petitparser.parser.repeating;

import org.petitparser.context.Context;
import org.petitparser.context.MultiLineStringBuffer;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A greedy parser that repeatedly parses between 'min' and 'max' instances of
 * its delegate.
 */
public class PossessiveRepeatingParser extends RepeatingParser {

	public PossessiveRepeatingParser(Parser delegate, int min, int max) {
		super(delegate, min, max);
	}

	@Override
	public Result parseOn(Context context) {
		Context current = context;
		List<Object> elements = new ArrayList<>();
		while (elements.size() < min) {
			Result result = delegate.parseOn(current);
			if (result.isFailure()) {
				return result;
			}
			elements.add(result.get());
			current = result;
		}
		while (max == UNBOUNDED || elements.size() < max) {
			Result result = delegate.parseOn(current);
			if (result.isFailure()) {
				return current.success(elements, context.getPosition(), current.getPosition());
			}
			elements.add(result.get());
			current = result;
		}
		return current.success(elements, context.getPosition(), current.getPosition());
	}

	@Override
	public int fastParseOn(MultiLineStringBuffer buffer, int position) {
		int count = 0;
		int current = position;
		while (count < min) {
			int result = delegate.fastParseOn(buffer, current);
			if (result < 0) {
				return result;
			}
			current = result;
			count++;
		}
		while (max == UNBOUNDED || count < max) {
			int result = delegate.fastParseOn(buffer, current);
			if (result < 0) {
				return current;
			}
			current = result;
			count++;
		}
		return current;
	}

	@Override
	public PossessiveRepeatingParser copy() {
		return new PossessiveRepeatingParser(delegate, min, max);
	}
}
