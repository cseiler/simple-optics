package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.ToIntFunction;

import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class IntView<A> extends View<A, Integer>
{

  public final ToIntFunction<A> fgetInt;

  public IntView(ToIntFunction<A> fgetInt)
  {
    super(a -> Integer.valueOf(fgetInt.applyAsInt(a)));
    this.fgetInt = fgetInt;
  }

  public int getAsInt(A a)
  {
    return fgetInt.applyAsInt(a);
  }

  public <C> IntView<C> compose(final View<C, A> before)
  {
    return new IntView<C>(
        c -> getAsInt(before.get(c)));
  }

  public <C> OptionalIntView<C> compose(final OptionalView<C, A> before)
  {
    return new OptionalIntView<C>(
        c -> {
          Optional<A> oa = before.get(c);
          return oa.isPresent()?OptionalInt.of(getAsInt(oa.get())):OptionalInt.empty();
        });
  }

}