package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalIntView<A> extends OptionalView<A, Integer>
{

  public final Function<Optional<A>, OptionalInt> fgetInt;

  public OptionalIntView(IntView<A> lens)
  {
    super(oa -> oa.isPresent()?Optional.of(Integer.valueOf(lens.fgetInt.applyAsInt(oa.get()))):Optional.empty());
    this.fgetInt = oa -> oa.isPresent()?OptionalInt.of(lens.fgetInt.applyAsInt(oa.get())):OptionalInt.empty();
  }

  public OptionalIntView(Function<Optional<A>, OptionalInt> fgetInt)
  {
    super(oa -> {
      OptionalInt oi = fgetInt.apply(oa);
      return oi.isPresent()?Optional.of(Integer.valueOf(oi.getAsInt())):Optional.empty();
    });
    this.fgetInt = fgetInt;
  }

  public OptionalInt getAsInt(Optional<A> a)
  {
    return fgetInt.apply(a);
  }

  public OptionalInt getAsInt(A a)
  {
    return fgetInt.apply(Optional.ofNullable(a));
  }

  public <C> OptionalIntView<C> compose(final View<C, A> that)
  {
    return new OptionalIntView<C>(
        oc -> oc.isPresent()?getAsInt(that.get(oc.get())):OptionalInt.empty());
  }

  public <C> OptionalIntView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalIntView<C>(
        oc -> oc.isPresent()?getAsInt(that.get(oc.get())):OptionalInt.empty());
  }

}