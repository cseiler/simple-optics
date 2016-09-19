package de.c.seiler.simpleoptics.primitive;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
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
public class OptionalDoubleLens<A> extends OptionalDoubleView<A>
{
  public final BiFunction<Optional<A>, OptionalDouble, Optional<A>> fset;

  public OptionalDoubleLens(DoubleLens<A> lens)
  {
    super(oa -> oa.isPresent()?OptionalDouble.of(lens.fgetDouble.applyAsDouble(oa.get())):OptionalDouble.empty());
    this.fset = (oa, ob) -> oa
        .map(a -> ob.isPresent()?lens.set(a, ob.getAsDouble()):a);
  }

  public OptionalDoubleLens(Function<Optional<A>, OptionalDouble> fget,
      BiFunction<Optional<A>, OptionalDouble, Optional<A>> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public Optional<A> set(Optional<A> a, OptionalDouble b)
  {
    return fset.apply(a, b);
  }

  public Optional<A> set(Optional<A> a, double b)
  {
    return fset.apply(a, OptionalDouble.of(b));
  }

  public Optional<A> set(A a, OptionalDouble b)
  {
    return fset.apply(Optional.ofNullable(a), b);
  }

  public Optional<A> set(A a, double b)
  {
    return fset.apply(Optional.ofNullable(a), OptionalDouble.of(b));
  }

  public Optional<A> mod(Optional<A> a, Function<OptionalDouble, OptionalDouble> f)
  {
    return set(a, f.apply(getAsDouble(a)));
  }

  public Optional<A> mod(A a, Function<OptionalDouble, OptionalDouble> f)
  {
    return set(a, f.apply(getAsDouble(a)));
  }

  public <C> OptionalDoubleLens<C> compose(final Lens<C, A> that)
  {
    return new OptionalDoubleLens<C>(
        c -> c.isPresent()?getAsDouble(that.get(c.get())):OptionalDouble.empty(),
        (c, b) -> c.flatMap(c2 -> {
          if (b.isPresent())
            return Optional
                .of(that.mod(c2, a -> set(a, b.getAsDouble()).orElseThrow(NoSuchElementException::new)));
          return Optional.empty();
        }));
  }

  public <C> OptionalDoubleLens<C> compose(final OptionalLens<C, A> that)
  {
    return new OptionalDoubleLens<C>(
        c -> getAsDouble(that.get(c)),
        (c, b) -> that.mod(c, a -> set(a, b)));
  }
}