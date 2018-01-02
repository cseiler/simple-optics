package de.c.seiler.simpleoptics.primitive;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalInt;
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
public class OptionalIntLens<A> extends OptionalIntView<A>
{
  public final BiFunction<Optional<A>, OptionalInt, Optional<A>> fset;

  public OptionalIntLens(IntLens<A> lens)
  {
    super(oa -> oa.isPresent()?OptionalInt.of(lens.fgetInt.applyAsInt(oa.get())):OptionalInt.empty());
    this.fset = (oa, ob) -> oa
        .map(a -> ob.isPresent()?lens.set(a, ob.getAsInt()):a);
  }

  public OptionalIntLens(Function<Optional<A>, OptionalInt> fget,
      BiFunction<Optional<A>, OptionalInt, Optional<A>> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public Optional<A> set(Optional<A> a, OptionalInt b)
  {
    return fset.apply(a, b);
  }

  public Optional<A> set(Optional<A> a, int b)
  {
    return fset.apply(a, OptionalInt.of(b));
  }

  public Optional<A> set(A a, OptionalInt b)
  {
    return fset.apply(Optional.ofNullable(a), b);
  }

  public Optional<A> set(A a, int b)
  {
    return fset.apply(Optional.ofNullable(a), OptionalInt.of(b));
  }

  public Optional<A> mod(Optional<A> a, Function<OptionalInt, OptionalInt> f)
  {
    return set(a, f.apply(getAsInt(a)));
  }

  public Optional<A> mod(A a, Function<OptionalInt, OptionalInt> f)
  {
    return set(a, f.apply(getAsInt(a)));
  }

  public <C> OptionalIntLens<C> compose(final Lens<C, A> before)
  {
    return new OptionalIntLens<C>(
        c -> c.isPresent()?getAsInt(before.get(c.get())):OptionalInt.empty(),
        (c, b) -> c.flatMap(c2 -> {
          if (b.isPresent())
            return Optional
                .of(before.mod(c2, a -> set(a, b.getAsInt()).orElseThrow(NoSuchElementException::new)));
          return Optional.empty();
        }));
  }

  public <C> OptionalIntLens<C> compose(final OptionalLens<C, A> before)
  {
    return new OptionalIntLens<C>(
        c -> getAsInt(before.get(c)),
        (c, b) -> before.mod(c, a -> set(a, b)));
  }
}