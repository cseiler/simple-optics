package de.c.seiler.simpleoptics;

import java.util.List;
import java.util.function.Function;

public abstract class BaseView<A, B, C> extends Fold<A, C> implements Function<A, B> {

	public final Function<A, B> fget;

	public BaseView(Function<A, B> fget, Function<A, List<C>> fcol) {
		super(fcol);
		this.fget = fget;
	}

	public B apply(A a) {
		return get(a);
	}

	public B get(A a) {
		return fget.apply(a);
	}

}
