package org.petitparser.parser.combinators;

import org.petitparser.context.Context;
import org.petitparser.context.Result;
import org.petitparser.parser.Parser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A parser that delegates to another one.
 */
public class DelegateParser extends Parser {

	protected Parser delegate;

	public DelegateParser(Parser delegate) {
		this.delegate = Objects.requireNonNull(delegate, "Undefined delegate parser");
		setName(delegate.getName());
	}

	@Override
	public Result parseOn(Context context) {
		return delegate.parseOn(context);
	}

	@Override
	public void replace(Parser source, Parser target) {
		super.replace(source, target);
		if (delegate == source) {
			delegate = target;
		}
	}

	@Override
	public List<Parser> getChildren() {
		return Collections.singletonList(delegate);
	}

	@Override
	public DelegateParser copy() {
		return new DelegateParser(delegate);
	}
}
