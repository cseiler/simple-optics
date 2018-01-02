package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.LongUnaryOperator;
import java.util.function.ToLongFunction;

import de.c.seiler.simpleoptics.Lens;
import de.c.seiler.simpleoptics.OptionalLens;
import de.c.seiler.simpleoptics.function.ObjLongFunction;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class LongLens<A> extends LongView<A>
{

  public final ObjLongFunction<A, A> fset;

  public LongLens(ToLongFunction<A> fget, ObjLongFunction<A, A> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public A set(A a, long b)
  {
    return fset.apply(a, b);
  }

  public A mod(A a, LongUnaryOperator f)
  {
    return set(a, f.applyAsLong(getAsLong(a)));
  }

  public <C> LongLens<C> compose(final Lens<C, A> before)
  {
    return new LongLens<C>(
        c -> getAsLong(before.get(c)),
        (c, b) -> before.mod(c, a -> set(a, b)));
  }

  public <C> OptionalLongLens<C> compose(final OptionalLens<C, A> before)
  {
    return new OptionalLongLens<C>(
        c -> {
          Optional<A> oa = before.get(c);
          return oa.isPresent()?OptionalLong.of(getAsLong(oa.get())):OptionalLong.empty();
        },
        (c, b) -> {
          if (b.isPresent())
            return before.mod(c, a -> a.map(a0 -> set(a0, b.getAsLong())));
          return Optional.empty();
        });
  }

}