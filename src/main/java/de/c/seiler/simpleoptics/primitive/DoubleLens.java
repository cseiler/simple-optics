package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ToDoubleFunction;

import de.c.seiler.simpleoptics.Lens;
import de.c.seiler.simpleoptics.OptionalLens;
import de.c.seiler.simpleoptics.function.ObjDoubleFunction;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * Inspired by and expanded upon: http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class DoubleLens<A> extends DoubleView<A>
{

  public final ObjDoubleFunction<A, A> fset;

  public DoubleLens(ToDoubleFunction<A> fget, ObjDoubleFunction<A, A> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public A set(A a, double b)
  {
    return fset.apply(a, b);
  }

  public A mod(A a, DoubleUnaryOperator f)
  {
    return set(a, f.applyAsDouble(getAsDouble(a)));
  }

  public <C> DoubleLens<C> compose(final Lens<C, A> before)
  {
    return new DoubleLens<C>(
        c -> getAsDouble(before.get(c)),
        (c, b) -> before.mod(c, a -> set(a, b)));
  }

  public <C> OptionalDoubleLens<C> compose(final OptionalLens<C, A> before)
  {
    return new OptionalDoubleLens<C>(
        c -> {
          Optional<A> oa = before.get(c);
          return oa.isPresent()?OptionalDouble.of(getAsDouble(oa.get())):OptionalDouble.empty();
        },
        (c, b) -> {
          if (b.isPresent())
            return before.mod(c, a -> a.map(a0 -> set(a0, b.getAsDouble())));
          return Optional.empty();
        });
  }

}