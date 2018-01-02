package de.c.seiler.simpleoptics;

import java.util.function.Function;

import de.c.seiler.simpleoptics.primitive.DoubleStreamView;
import de.c.seiler.simpleoptics.primitive.DoubleView;
import de.c.seiler.simpleoptics.primitive.IntStreamView;
import de.c.seiler.simpleoptics.primitive.IntView;
import de.c.seiler.simpleoptics.primitive.LongStreamView;
import de.c.seiler.simpleoptics.primitive.LongView;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleView;
import de.c.seiler.simpleoptics.primitive.OptionalIntView;
import de.c.seiler.simpleoptics.primitive.OptionalLongView;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class View<A, B> implements Function<A, B>
{

  public final Function<A, B> fget;

  public View(Function<A, B> fget)
  {
    this.fget = fget;
  }

  @Override
  public B apply(A a)
  {
    return get(a);
  }

  public B get(A a)
  {
    return fget.apply(a);
  }

  public <C> View<C, B> compose(final View<C, A> that)
  {
    return new View<C, B>(c -> get(that.get(c)));
  }

  public <C> OptionalView<C, B> compose(final OptionalView<C, A> that)
  {
    return new OptionalView<C, B>(c -> that.get(c).map(c1 -> get(c1)));
  }

  public <C> StreamView<C, B> compose(final StreamView<C, A> that)
  {
    return new StreamView<C, B>(c -> that.get(c).map(fget));
  }

  public <C> StreamView<A, C> andThen(StreamView<B, C> that)
  {
    return that.compose(this);
  }

  public IntStreamView<A> andThen(IntStreamView<B> that)
  {
    return that.compose(this);
  }

  public LongStreamView<A> andThen(LongStreamView<B> that)
  {
    return that.compose(this);
  }

  public DoubleStreamView<A> andThen(DoubleStreamView<B> that)
  {
    return that.compose(this);
  }

  public <C> View<A, C> andThen(View<B, C> that)
  {
    return that.compose(this);
  }

  public <C> OptionalView<A, C> andThen(OptionalView<B, C> that)
  {
    return that.compose(this);
  }

  public IntView<A> andThen(IntView<B> that)
  {
    return that.compose(this);
  }

  public OptionalIntView<A> andThen(OptionalIntView<B> that)
  {
    return that.compose(this);
  }

  public LongView<A> andThen(LongView<B> that)
  {
    return that.compose(this);
  }

  public OptionalLongView<A> andThen(OptionalLongView<B> that)
  {
    return that.compose(this);
  }

  public DoubleView<A> andThen(DoubleView<B> that)
  {
    return that.compose(this);
  }

  public OptionalDoubleView<A> andThen(OptionalDoubleView<B> that)
  {
    return that.compose(this);
  }

}