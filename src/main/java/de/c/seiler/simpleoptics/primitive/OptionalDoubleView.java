package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalDoubleView<A> extends OptionalView<A, Double>
{

  public final Function<Optional<A>, OptionalDouble> fgetDouble;

  public OptionalDoubleView(DoubleView<A> lens)
  {
    super(oa -> oa.isPresent()?Optional.of(Double.valueOf(lens.fgetDouble.applyAsDouble(oa.get()))):Optional.empty());
    this.fgetDouble = oa -> oa.isPresent()?OptionalDouble.of(lens.fgetDouble.applyAsDouble(oa.get()))
        :OptionalDouble.empty();
  }

  public OptionalDoubleView(Function<Optional<A>, OptionalDouble> fgetDouble)
  {
    super(oa -> {
      OptionalDouble oi = fgetDouble.apply(oa);
      return oi.isPresent()?Optional.of(Double.valueOf(oi.getAsDouble())):Optional.empty();
    });
    this.fgetDouble = fgetDouble;
  }

  public OptionalDouble getAsDouble(Optional<A> a)
  {
    return fgetDouble.apply(a);
  }

  public OptionalDouble getAsDouble(A a)
  {
    return fgetDouble.apply(Optional.ofNullable(a));
  }

  public <C> OptionalDoubleView<C> compose(final View<C, A> before)
  {
    return new OptionalDoubleView<C>(
        oc -> oc.isPresent()?getAsDouble(before.get(oc.get())):OptionalDouble.empty());
  }

  public <C> OptionalDoubleView<C> compose(final OptionalView<C, A> before)
  {
    return new OptionalDoubleView<C>(
        oc -> oc.isPresent()?getAsDouble(before.get(oc.get())):OptionalDouble.empty());
  }

}