package de.c.seiler.simpleoptics;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import de.c.seiler.simpleoptics.primitive.DoubleLens;
import de.c.seiler.simpleoptics.primitive.IntLens;
import de.c.seiler.simpleoptics.primitive.LongLens;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleLens;
import de.c.seiler.simpleoptics.primitive.OptionalIntLens;
import de.c.seiler.simpleoptics.primitive.OptionalLongLens;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class Lens<A, B> extends View<A, B>
{

  public final BiFunction<A, B, A> fset;

  public Lens(Function<A, B> fget, BiFunction<A, B, A> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public A set(A a, B b)
  {
    return fset.apply(a, b);
  }

  public A mod(A a, UnaryOperator<B> f)
  {
    return set(a, f.apply(get(a)));
  }

  public <C> Lens<C, B> compose(final Lens<C, A> that)
  {
    return new Lens<C, B>(
        c -> get(that.get(c)),
        (c, b) -> that.mod(c, a -> set(a, b)));
  }

  public <C> OptionalLens<C, B> compose(final OptionalLens<C, A> that)
  {
    return new OptionalLens<C, B>(
        c -> that.get(c).map(c1 -> get(c1)),
        (c, b) -> b.flatMap(b1 -> that.mod(c, a -> a.map(a1 -> set(a1, b1)))));
  }

  public <C> Lens<A, C> andThen(Lens<B, C> that)
  {
    return that.compose(this);
  }

  public <C> OptionalLens<A, C> andThen(OptionalLens<B, C> that)
  {
    return that.compose(this);
  }

  public IntLens<A> andThen(IntLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalIntLens<A> andThen(OptionalIntLens<B> that)
  {
    return that.compose(this);
  }

  public LongLens<A> andThen(LongLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalLongLens<A> andThen(OptionalLongLens<B> that)
  {
    return that.compose(this);
  }

  public DoubleLens<A> andThen(DoubleLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalDoubleLens<A> andThen(OptionalDoubleLens<B> that)
  {
    return that.compose(this);
  }

}