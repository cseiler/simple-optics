package de.c.seiler.simpleoptics.primitive;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.c.seiler.simpleoptics.Lens;
import de.c.seiler.simpleoptics.OptionalLens;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalLongLens<A> extends OptionalLongView<A>
{
  public final BiFunction<Optional<A>, OptionalLong, Optional<A>> fset;

  public OptionalLongLens(LongLens<A> lens)
  {
    super(oa -> oa.isPresent()?OptionalLong.of(lens.fgetLong.applyAsLong(oa.get())):OptionalLong.empty());
    this.fset = (oa, ob) -> oa
        .map(a -> ob.isPresent()?lens.set(a, ob.getAsLong()):a);
  }

  public OptionalLongLens(Function<Optional<A>, OptionalLong> fget,
      BiFunction<Optional<A>, OptionalLong, Optional<A>> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public Optional<A> set(Optional<A> a, OptionalLong b)
  {
    return fset.apply(a, b);
  }

  public Optional<A> set(Optional<A> a, long b)
  {
    return fset.apply(a, OptionalLong.of(b));
  }

  public Optional<A> set(A a, OptionalLong b)
  {
    return fset.apply(Optional.ofNullable(a), b);
  }

  public Optional<A> set(A a, long b)
  {
    return fset.apply(Optional.ofNullable(a), OptionalLong.of(b));
  }

  public Optional<A> mod(Optional<A> a, Function<OptionalLong, OptionalLong> f)
  {
    return set(a, f.apply(getAsLong(a)));
  }

  public Optional<A> mod(A a, Function<OptionalLong, OptionalLong> f)
  {
    return set(a, f.apply(getAsLong(a)));
  }

  public <C> OptionalLongLens<C> compose(final Lens<C, A> before)
  {
    return new OptionalLongLens<C>(
        c -> c.isPresent()?getAsLong(before.get(c.get())):OptionalLong.empty(),
        (c, b) -> c.flatMap(c2 -> {
          if (b.isPresent())
            return Optional
                .of(before.mod(c2, a -> set(a, b.getAsLong()).orElseThrow(NoSuchElementException::new)));
          return Optional.empty();
        }));
  }

  public <C> OptionalLongLens<C> compose(final OptionalLens<C, A> before)
  {
    return new OptionalLongLens<C>(
        c -> getAsLong(before.get(c)),
        (c, b) -> before.mod(c, a -> set(a, b)));
  }
}