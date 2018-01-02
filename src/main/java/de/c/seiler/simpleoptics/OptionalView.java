package de.c.seiler.simpleoptics;

import java.util.Optional;
import java.util.function.Function;

import de.c.seiler.simpleoptics.primitive.DoubleStreamView;
import de.c.seiler.simpleoptics.primitive.IntStreamView;
import de.c.seiler.simpleoptics.primitive.LongStreamView;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleView;
import de.c.seiler.simpleoptics.primitive.OptionalIntView;
import de.c.seiler.simpleoptics.primitive.OptionalLongView;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalView<A, B> implements Function<Optional<A>, Optional<B>>
{

  public final Function<Optional<A>, Optional<B>> fget;

  public OptionalView(View<A, B> view)
  {
    this.fget = oa -> oa.flatMap(a -> Optional.ofNullable(view.fget.apply(a)));
  }

  public OptionalView(Function<Optional<A>, Optional<B>> fget)
  {
    this.fget = fget;
  }

  public Optional<B> get(Optional<A> a)
  {
    return fget.apply(a);
  }

  @Override
  public Optional<B> apply(Optional<A> a)
  {
    return get(a);
  }

  public Optional<B> get(A a)
  {
    return fget.apply(Optional.ofNullable(a));
  }

  public <C> OptionalView<C, B> compose(final View<C, A> that)
  {
    return new OptionalView<C, B>(c -> c.flatMap(c1 -> get(that.get(c1))));
  }

  public <C> OptionalView<C, B> compose(final OptionalView<C, A> that)
  {
    return new OptionalView<C, B>(c -> get(that.get(c)));
  }

  public <C> OptionalView<A, C> andThen(OptionalView<B, C> that)
  {
    return that.compose(this);
  }

  public <C> OptionalView<A, C> andThen(View<B, C> that)
  {
    return that.compose(this);
  }

  public OptionalIntView<A> andThen(OptionalIntView<B> that)
  {
    return that.compose(this);
  }

  public OptionalLongView<A> andThen(OptionalLongView<B> that)
  {
    return that.compose(this);
  }

  public OptionalDoubleView<A> andThen(OptionalDoubleView<B> that)
  {
    return that.compose(this);
  }

  public <C> OptionalStreamView<A, C> andThen(StreamView<B, C> that)
  {
    return that.composeFlatMap(this);
  }

  public IntStreamView<A> andThen(IntStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public LongStreamView<A> andThen(LongStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public DoubleStreamView<A> andThen(DoubleStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  
}