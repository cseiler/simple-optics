package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.LongStream;

import de.c.seiler.simpleoptics.OptionalStreamView;
import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.StreamView;
import de.c.seiler.simpleoptics.View;

public class OptionalLongStreamView<A> extends OptionalView<A, LongStream>
{

  public OptionalLongStreamView(LongStreamView<A> view)
  {
    super(view);
  }

  public OptionalLongStreamView(Function<Optional<A>, Optional<LongStream>> fget)
  {
    super(fget);
  }

  public Optional<LongStream> get(Optional<A> a)
  {
    return fget.apply(a);
  }

  @Override
  public Optional<LongStream> apply(Optional<A> a)
  {
    return fget.apply(a);
  }

  public Optional<LongStream> get(A a)
  {
    return fget.apply(Optional.ofNullable(a));
  }

  public <C> OptionalLongStreamView<C> compose(final View<C, A> that)
  {
    return new OptionalLongStreamView<C>(c -> c.flatMap(cc -> get(Optional.of(that.get(cc)))));
  }

  public <C> OptionalLongStreamView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalLongStreamView<C>(c -> c.flatMap(cc -> get(that.get(cc))));
  }

  public <C> OptionalLongStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new OptionalLongStreamView<C>(c -> c
        .map(cc -> that.get(cc)
            .map(Optional::of)
            .map(fget)
            .flatMapToLong(os -> os.orElseGet(LongStream::empty))));
  }

  public <C> OptionalLongStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalLongStreamView<C>(c -> that.get(c)
        .map(sta -> sta.map(Optional::of))
        .map(stoa -> stoa.map(fget))
        .map(sosb -> sosb
            .flatMapToLong(osb -> osb.orElseGet(LongStream::empty))));
  }
}
