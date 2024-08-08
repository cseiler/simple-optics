package de.c.seiler.simpleoptics;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Fold<A, C> {

	private final Function<A, List<C>> flget;

	public Fold(Function<A, List<C>> flget) {
		this.flget = flget;
	}

	public Stream<C> toStream(A a) {
		return flget.apply(a).stream();
	}

	public List<C> toList(A a) {
		return flget.apply(a);
	}

	public <D, E extends List<D>, ACC> Fold<A, D> fold(Collector<C, ACC, E> collector) {
		return new Fold<>(a -> toStream(a).collect(collector));
	}

	public <D> Fold<A, D> each(View<C, D> v) {
		return new Fold<>(a -> toStream(a).map(v).toList());
	}

	public  Fold<A, C> filter(Predicate<C> pred) {
		return new Fold<>(a -> toStream(a).filter(pred).toList());
	}

}
