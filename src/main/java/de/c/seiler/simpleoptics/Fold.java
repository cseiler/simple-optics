package de.c.seiler.simpleoptics;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class Fold<A, B> {

	private Function<A, Collection<B>> fcol;

	public Fold(Function<A, Collection<B>> fcol) {
		this.fcol = fcol;
	}

	public Stream<B> toStream(A a) {
		return fcol.apply(a).stream();
	}

	public List<B> toList(A a) {
		return List.copyOf(fcol.apply(a));
	}

	public <D, C extends Collection<D>, ACC> Fold<A, D> fold(Collector<B, ACC, C> collector) {
		return new Fold<>(a -> toStream(a).collect(collector));
	}

	public <C> Fold<A, C> each(View<B, C> v) {
		return new Fold<>(a -> toStream(a).map(v).toList());
	}

	public Fold<A, B> filter(Predicate<B> pred) {
		return new Fold<>(a -> toStream(a).filter(pred).toList());
	}

}
