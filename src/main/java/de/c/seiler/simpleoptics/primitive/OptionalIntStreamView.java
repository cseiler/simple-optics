package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import de.c.seiler.simpleoptics.OptionalStreamView;
import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.StreamView;
import de.c.seiler.simpleoptics.View;

public class OptionalIntStreamView<A> extends OptionalView<A, IntStream>
{

  public OptionalIntStreamView(IntStreamView<A> view)
  {
    super(view);
  }

  public OptionalIntStreamView(Function<Optional<A>, Optional<IntStream>> fget)
  {
    super(fget);
  }

  public Optional<IntStream> get(Optional<A> a)
  {
    return fget.apply(a);
  }

  @Override
  public Optional<IntStream> apply(Optional<A> a)
  {
    return fget.apply(a);
  }

  public Optional<IntStream> get(A a)
  {
    return fget.apply(Optional.ofNullable(a));
  }

  public <C> OptionalIntStreamView<C> compose(final View<C, A> that)
  {
    return new OptionalIntStreamView<C>(c -> c.flatMap(cc -> get(Optional.of(that.get(cc)))));
  }

  public <C> OptionalIntStreamView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalIntStreamView<C>(c -> c.flatMap(cc -> get(that.get(cc))));
  }

  public <C> OptionalIntStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new OptionalIntStreamView<C>(c -> c
        .map(cc -> that.get(cc)
            .map(Optional::of)
            .map(fget)
            .flatMapToInt(os -> os.orElseGet(IntStream::empty))));
  }

  public <C> OptionalIntStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalIntStreamView<C>(c -> that.get(c)
        .map(sta -> sta.map(Optional::of))
        .map(stoa -> stoa.map(fget))
        .map(sosb -> sosb
            .flatMapToInt(osb -> osb.orElseGet(IntStream::empty))));
  }
}
