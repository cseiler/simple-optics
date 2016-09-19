package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.ToLongFunction;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class LongView<A> extends View<A, Long>
{

  public final ToLongFunction<A> fgetLong;

  public LongView(ToLongFunction<A> fgetLong)
  {
    super(a -> Long.valueOf(fgetLong.applyAsLong(a)));
    this.fgetLong = fgetLong;
  }

  public long getAsLong(A a)
  {
    return fgetLong.applyAsLong(a);
  }

  public <C> LongView<C> compose(final View<C, A> that)
  {
    return new LongView<C>(
        c -> getAsLong(that.get(c)));
  }

  public <C> OptionalLongView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalLongView<C>(
        c -> {
          Optional<A> oa = that.get(c);
          return oa.isPresent()?OptionalLong.of(getAsLong(oa.get())):OptionalLong.empty();
        });
  }

}