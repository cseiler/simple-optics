package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class DoubleView<A> extends View<A, Double>
{

  public final ToDoubleFunction<A> fgetDouble;

  public DoubleView(ToDoubleFunction<A> fgetDouble)
  {
    super(a -> Double.valueOf(fgetDouble.applyAsDouble(a)));
    this.fgetDouble = fgetDouble;
  }

  public double getAsDouble(A a)
  {
    return fgetDouble.applyAsDouble(a);
  }

  public <C> DoubleView<C> compose(final View<C, A> before)
  {
    return new DoubleView<C>(
        c -> getAsDouble(before.get(c)));
  }

  public <C> OptionalDoubleView<C> compose(final OptionalView<C, A> before)
  {
    return new OptionalDoubleView<C>(
        c -> {
          Optional<A> oa = before.get(c);
          return oa.isPresent()?OptionalDouble.of(getAsDouble(oa.get())):OptionalDouble.empty();
        });
  }

}