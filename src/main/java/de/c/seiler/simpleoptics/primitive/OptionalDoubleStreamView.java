package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.DoubleStream;

import de.c.seiler.simpleoptics.OptionalStreamView;
import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.StreamView;
import de.c.seiler.simpleoptics.View;

public class OptionalDoubleStreamView<A> extends OptionalView<A, DoubleStream>
{
  public OptionalDoubleStreamView(DoubleStreamView<A> view)
  {
    super(view);
  }

  public OptionalDoubleStreamView(Function<Optional<A>, Optional<DoubleStream>> fget)
  {
    super(fget);
  }

  public Optional<DoubleStream> get(Optional<A> a)
  {
    return fget.apply(a);
  }

  @Override
  public Optional<DoubleStream> apply(Optional<A> a)
  {
    return fget.apply(a);
  }

  public Optional<DoubleStream> get(A a)
  {
    return fget.apply(Optional.ofNullable(a));
  }

  public <C> OptionalDoubleStreamView<C> compose(final View<C, A> that)
  {
    return new OptionalDoubleStreamView<C>(c -> c.flatMap(cc -> get(Optional.of(that.get(cc)))));
  }

  public <C> OptionalDoubleStreamView<C> compose(final OptionalView<C, A> that)
  {
    return new OptionalDoubleStreamView<C>(c -> c.flatMap(cc -> get(that.get(cc))));
  }

  public <C> OptionalDoubleStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new OptionalDoubleStreamView<C>(c -> c
        .map(cc -> that.get(cc)
            .map(Optional::of)
            .map(fget)
            .flatMapToDouble(os -> os.orElseGet(DoubleStream::empty))));
  }

  public <C> OptionalDoubleStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalDoubleStreamView<C>(c -> that.get(c)
        .map(sta -> sta.map(Optional::of))
        .map(stoa -> stoa.map(fget))
        .map(sosb -> sosb
            .flatMapToDouble(osb -> osb.orElseGet(DoubleStream::empty))));
  }
}
