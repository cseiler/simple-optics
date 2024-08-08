package de.c.seiler.simpleoptics;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Fold<A, B, C> implements Function<A, B> {

	public final Function<A, B> fget;
	private final Function<A, List<C>> fcol;

	public Fold(Function<A, B> fget, Function<A, List<C>> fcol) {
		this.fget = fget;
		this.fcol = fcol;
	}

	public B apply(A a) {
		return get(a);
	}

	public B get(A a) {
		return fget.apply(a);
	}

	public Stream<C> toStream(A a) {
		return fcol.apply(a).stream();
	}

	public List<C> toList(A a) {
		return fcol.apply(a);
	}

	public <D, E extends List<D>, ACC> Fold<A, E, D> fold(Collector<C, ACC, E> collector) {
		return new Fold<>(a -> toStream(a).collect(collector), a -> toStream(a).collect(collector));
	}

	public <D> Fold<A, List<D>, D> each(View<C, D> v) {
		return new Fold<>(a -> toStream(a).map(v).toList(), a -> toStream(a).map(v).toList());
	}

	public  Fold<A,List<C>, C> filter(Predicate<C> pred) {
		return new Fold<>(a -> toStream(a).filter(pred).toList(), a -> toStream(a).filter(pred).toList());
	}

}
