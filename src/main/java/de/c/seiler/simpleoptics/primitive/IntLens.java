package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;

import de.c.seiler.simpleoptics.Lens;
import de.c.seiler.simpleoptics.OptionalLens;
import de.c.seiler.simpleoptics.function.ObjIntFunction;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class IntLens<A> extends IntView<A>
{

  public final ObjIntFunction<A, A> fset;

  public IntLens(ToIntFunction<A> fgetInt, ObjIntFunction<A, A> fset)
  {
    super(fgetInt);
    this.fset = fset;
  }

  public A set(A a, int b)
  {
    return fset.apply(a, b);
  }

  public A mod(A a, IntUnaryOperator f)
  {
    return set(a, f.applyAsInt(getAsInt(a)));
  }

  public <C> IntLens<C> compose(final Lens<C, A> that)
  {
    return new IntLens<C>(
        c -> getAsInt(that.get(c)),
        (c, b) -> that.mod(c, a -> set(a, b)));
  }

  public <C> OptionalIntLens<C> compose(final OptionalLens<C, A> that)
  {
    return new OptionalIntLens<C>(
        c -> {
          Optional<A> oa = that.get(c);
          return oa.isPresent()?OptionalInt.of(getAsInt(oa.get())):OptionalInt.empty();
        },
        (c, b) -> {
          if (b.isPresent())
            return that.mod(c, a -> a.map(a0 -> set(a0, b.getAsInt())));
          return Optional.empty();
        });
  }

}