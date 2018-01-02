package de.c.seiler.simpleoptics;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import de.c.seiler.simpleoptics.primitive.DoubleStreamView;
import de.c.seiler.simpleoptics.primitive.IntStreamView;
import de.c.seiler.simpleoptics.primitive.LongStreamView;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleStreamView;
import de.c.seiler.simpleoptics.primitive.OptionalIntStreamView;
import de.c.seiler.simpleoptics.primitive.OptionalLongStreamView;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class OptionalStreamView<A, B> extends OptionalView<A, Stream<B>>
{
  
  public OptionalStreamView(StreamView<A, B> view)
  {
    super(oa -> oa.flatMap(a -> Optional.ofNullable(view.fget.apply(a))));
  }

  public OptionalStreamView(Function<Optional<A>, Optional<Stream<B>>> fget)
  {
    super(fget);
  }

  public Optional<Stream<B>> get(Optional<A> a)
  {
    return fget.apply(a);
  }

  @Override
  public Optional<Stream<B>> apply(Optional<A> a)
  {
    return fget.apply(a);
  }

  public Optional<Stream<B>> get(A a)
  {
    return fget.apply(Optional.ofNullable(a));
  }

  public <C> OptionalStreamView<C, B> compose(final View<C, A> that)
  {
    return new OptionalStreamView<C, B>(c -> c.flatMap(cc -> get(Optional.of(that.get(cc)))));
  }

  public <C> OptionalStreamView<C, B> compose(final OptionalView<C, A> that)
  {
    return new OptionalStreamView<C, B>(c -> get(that.get(c)));
  }

  public <C> OptionalStreamView<C, B> composeFlatMap(final StreamView<C, A> that)
  {
    return new OptionalStreamView<C, B>(c -> c
        .map(cc -> that.get(cc)
            .map(Optional::of)
            .map(fget)
            .flatMap(os -> os.orElseGet(Stream::empty))));
  }

  public <C> OptionalStreamView<C, B> composeFlatMap(final OptionalStreamView<C, A> that)
  {
    return new OptionalStreamView<C, B>(c -> that.get(c)
        .map(sta -> sta.map(Optional::of))
        .map(stoa -> stoa.map(fget))
        .map(sosb -> sosb
            .flatMap(osb -> osb.orElseGet(Stream::empty))));
  }

  public <C> OptionalStreamView<A, C> flatMap(final OptionalStreamView<B, C> that)
  {
    return that.composeFlatMap(this);
  }

  public <C> OptionalStreamView<A, C> flatMap(final StreamView<B, C> that)
  {
    return that.composeFlatMap(this);
  }

  public OptionalIntStreamView<A> flatMap(final IntStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public OptionalLongStreamView<A> flatMap(final LongStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public OptionalDoubleStreamView<A> flatMap(final DoubleStreamView<B> that)
  {
    return that.composeFlatMap(this);
  }

  public <C> OptionalStreamView<A, C> map(final View<B, C> that)
  {
    return new OptionalStreamView<>(a -> get(a).map(sb -> sb.map(b -> that.get(b))));
  }

}