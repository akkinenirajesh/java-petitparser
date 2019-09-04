package org.petitparser.parser.actions;

import org.petitparser.context.Context;
import org.petitparser.context.MultiLineStringBuffer;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;
import org.petitparser.parser.combinators.DelegateParser;

import java.util.Objects;
import java.util.function.Function;

/**
 * A parser that performs a transformation with a given function on the
 * successful parse result of the delegate.
 *
 * @param <T> The type of the function argument.
 * @param <R> The type of the function result.
 */
public class ActionParserWithResult<R> extends DelegateParser {

	protected final Function<Result, R> function;
	protected final boolean hasSideEffects;

	public ActionParserWithResult(Parser delegate, Function<Result, R> function) {
		this(delegate, function, false);
	}

	public ActionParserWithResult(Parser delegate, Function<Result, R> function, boolean hasSideEffects) {
		super(delegate);
		this.function = Objects.requireNonNull(function, "Undefined function");
		this.hasSideEffects = hasSideEffects;
	}

	@Override
	public Result parseOn(Context context) {
		Result result = delegate.parseOn(context);
		if (result.isSuccess()) {
			return result.success(function.apply(result), context.getPosition(), result.getPosition());
		} else {
			return result;
		}
	}

	@Override
	public int fastParseOn(MultiLineStringBuffer buffer, int position) {
		// If we know to have side-effects, we have to fall back to the slow mode.
		return hasSideEffects ? super.fastParseOn(buffer, position) : delegate.fastParseOn(buffer, position);
	}

	@Override
	protected boolean hasEqualProperties(Parser other) {
		return super.hasEqualProperties(other) && Objects.equals(function, ((ActionParserWithResult<R>) other).function)
				&& hasSideEffects == ((ActionParserWithResult<R>) other).hasSideEffects;
	}

	@Override
	public ActionParserWithResult<R> copy() {
		return new ActionParserWithResult<>(delegate, function, hasSideEffects);
	}
}
