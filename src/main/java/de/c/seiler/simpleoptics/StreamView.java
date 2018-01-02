package de.c.seiler.simpleoptics;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import de.c.seiler.simpleoptics.primitive.DoubleStreamView;
import de.c.seiler.simpleoptics.primitive.IntStreamView;
import de.c.seiler.simpleoptics.primitive.LongStreamView;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class StreamView<A, B> extends View<A, Stream<B>>
{

  public StreamView(Function<A, Stream<B>> fget)
  {
    super(fget);
  }

  public <C> StreamView<C, B> compose(final View<C, A> that)
  {
    return new StreamView<C, B>(c -> get(that.get(c)));
  }

  public <C> OptionalStreamView<C, B> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalStreamView<>(c -> c
        .flatMap(cc -> that.get(cc)
            .map(sa -> sa
                .flatMap(fget))));
  }

  public <C> OptionalStreamView<A, C> flatMap(OptionalStreamView<B, C> that)
  {
    return that.composeFlatMap(this);
  }

  public <C> OptionalStreamView<C, B> composeFlatMap(final OptionalView<C, A> that)
  {
    return new OptionalStreamView<C, B>((Optional<C> c) -> c.flatMap(cc -> that.get(cc).map(a -> get(a))));
  }

  public <C> StreamView<C, B> composeFlatMap(final StreamView<C, A> that)
  {
    return new StreamView<C, B>(c -> that.get(c).flatMap(fget));
  }

  public <C> StreamView<A, C> flatMap(final StreamView<B, C> that)
  {
    return that.composeFlatMap(this);
  }

  public IntStreamView<A> flatMap(final IntStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public LongStreamView<A> flatMap(final LongStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public DoubleStreamView<A> flatMap(final DoubleStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public <C> StreamView<A, C> map(final View<B, C> that)
  {
    return that.compose(this);
  }
}