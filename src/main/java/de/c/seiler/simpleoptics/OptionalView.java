package de.c.seiler.simpleoptics;

import java.util.Optional;
import java.util.function.Function;

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
public class OptionalView<A, B> extends Fold<Optional<A>, B> implements Function<Optional<A>, Optional<B>> {


	private Function<Optional<A>, Optional<B>> fget;

	public OptionalView(View<A, B> view) {
		super(oa -> oa.map(a -> view.apply(a)).or(Optional::empty).stream().toList());
		this.fget = oa -> oa.map(a -> view.apply(a));
	}

	public OptionalView(Function<Optional<A>, Optional<B>> fget) {
		super(a -> fget.apply(a).stream().toList());
		this.fget = fget;
	}

	public Optional<B> get(Optional<A> a) {
		return apply(a);
	}

	@Override
	public Optional<B> apply(Optional<A> a) {
		return fget.apply(a);
	}

	public Optional<B> get(A a) {
		return fget.apply(Optional.ofNullable(a));
	}

	public <C> OptionalView<C, B> compose(final View<C, A> that) {
		return new OptionalView<C, B>(c -> c.flatMap(c1 -> get(that.get(c1))));
	}

	public <C> OptionalView<C, B> compose(final OptionalView<C, A> that) {
		return new OptionalView<C, B>(c -> get(that.get(c)));
	}

	public <C> OptionalView<A, C> andThen(OptionalView<B, C> that) {
		return that.compose(this);
	}

	public <C> OptionalView<A, C> andThen(View<B, C> that) {
		return that.compose(this);
	}

	public OptionalIntView<A> andThen(OptionalIntView<B> that) {
		return that.compose(this);
	}

	public OptionalLongView<A> andThen(OptionalLongView<B> that) {
		return that.compose(this);
	}

	public OptionalDoubleView<A> andThen(OptionalDoubleView<B> that) {
		return that.compose(this);
	}

}