package de.c.seiler.simpleoptics;

import java.util.List;
import java.util.function.Function;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class ListView<A, C> extends Fold<A, C>  implements Function<A, List<C>> {

	private Function<A, List<C>> fget;

	public ListView(Function<A, List<C>> flget) {
		super(flget);
		this.fget = flget;
	}
	
	public List<C> apply(A a) {
		return fget.apply(a);
	}

	public List<C> get(A a) {
		return apply(a);
	}

	public <D> ListView<D, C> compose(final View<D, A> that) {
		return new ListView<D, C>(d -> get(that.get(d)));
	}

	public <D> ListView<D, C> compose(final ListView<D, A> that) {
		return new ListView<D, C>(d -> that.get(d).stream().flatMap(this.andThen(List::stream)).toList());
	}

	public <D> ListView<D, C> compose(final OptionalView<D, A> that) {
		return new ListView<>(d -> that.get(d).map(c -> get(c)).orElse(List.of()));
	}

	public <D> ListView<A, D> andThen(ListView<C, D> that) {
		return that.compose(this);
	}

}