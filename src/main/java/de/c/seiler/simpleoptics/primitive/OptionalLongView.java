package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Function;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalLongView<A> extends OptionalView<A, Long>
{

  public final Function<Optional<A>, OptionalLong> fgetLong;

  public OptionalLongView(LongView<A> lens)
  {
    super(oa -> oa.isPresent()?Optional.of(Long.valueOf(lens.fgetLong.applyAsLong(oa.get()))):Optional.empty());
    this.fgetLong = oa -> oa.isPresent()?OptionalLong.of(lens.fgetLong.applyAsLong(oa.get())):OptionalLong.empty();
  }

  public OptionalLongView(Function<Optional<A>, OptionalLong> fgetLong)
  {
    super(oa -> {
      OptionalLong oi = fgetLong.apply(oa);
      return oi.isPresent()?Optional.of(Long.valueOf(oi.getAsLong())):Optional.empty();
    });
    this.fgetLong = fgetLong;
  }

  public OptionalLong getAsLong(Optional<A> a)
  {
    return fgetLong.apply(a);
  }

  public OptionalLong getAsLong(A a)
  {
    return fgetLong.apply(Optional.ofNullable(a));
  }

  public <C> OptionalLongView<C> compose(final View<C, A> that)
  {
    return new OptionalLongView<C>(
        oc -> oc.isPresent()?getAsLong(that.get(oc.get())):OptionalLong.empty());
  }

  public <C> OptionalLongView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalLongView<C>(
        oc -> oc.isPresent()?getAsLong(that.get(oc.get())):OptionalLong.empty());
  }

}