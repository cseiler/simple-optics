package de.c.seiler.simpleoptics;

import java.util.function.Function;

import de.c.seiler.simpleoptics.primitive.DoubleView;
import de.c.seiler.simpleoptics.primitive.IntView;
import de.c.seiler.simpleoptics.primitive.LongView;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleView;
import de.c.seiler.simpleoptics.primitive.OptionalIntView;
import de.c.seiler.simpleoptics.primitive.OptionalLongView;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class View<A, B> implements Function<A, B> {

	public final Function<A, B> fget;

	public View(Function<A, B> fget) {
		this.fget = fget;
	}

	@Override
	public B apply(A a) {
		return get(a);
	}

	public B get(A a) {
		return fget.apply(a);
	}

	public <C> View<C, B> compose(final View<C, A> before) {
		return new View<C, B>(c -> get(before.get(c)));
	}

	public <C> OptionalView<C, B> compose(final OptionalView<C, A> before) {
		return new OptionalView<C, B>(c -> before.get(c).map(c1 -> get(c1)));
	}

	public <C> View<A, C> andThen(View<B, C> that) {
		return that.compose(this);
	}

	public <C> OptionalView<A, C> andThen(OptionalView<B, C> that) {
		return that.compose(this);
	}

	public <C> IntView<A> andThen(IntView<B> that) {
		return that.compose(this);
	}

	public <C> OptionalIntView<A> andThen(OptionalIntView<B> that) {
		return that.compose(this);
	}

	public <C> LongView<A> andThen(LongView<B> that) {
		return that.compose(this);
	}

	public <C> OptionalLongView<A> andThen(OptionalLongView<B> that) {
		return that.compose(this);
	}

	public <C> DoubleView<A> andThen(DoubleView<B> that) {
		return that.compose(this);
	}

	public <C> OptionalDoubleView<A> andThen(OptionalDoubleView<B> that) {
		return that.compose(this);
	}

}