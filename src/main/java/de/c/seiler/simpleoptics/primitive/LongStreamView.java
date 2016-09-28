package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.LongStream;

import de.c.seiler.simpleoptics.OptionalStreamView;
import de.c.seiler.simpleoptics.OptionalView;
import de.c.seiler.simpleoptics.StreamView;
import de.c.seiler.simpleoptics.View;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of
 * immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * 
 * @param <A>
 * @param <B>
 */
public class LongStreamView<A> extends View<A, LongStream>
{

  public LongStreamView(Function<A, LongStream> fget)
  {
    super(fget);
  }

  public <C> LongStreamView<C> compose(final View<C, A> that)
  {
    return new LongStreamView<C>(c -> get(that.get(c)));
  }

  public <C> LongStreamView<C> composeFlatMap(final OptionalView<C, A> that)
  {
    return new LongStreamView<C>(c -> {
      Optional<A> oa = that.get(c);
      return oa.isPresent()?get(oa.get()):LongStream.empty();
    });
  }

  public <C> LongStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new LongStreamView<C>(c -> that.get(c).flatMapToLong(fget));
  }

  public <C> OptionalLongStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalLongStreamView<C>(c -> c
        .flatMap(cc -> that.get(cc)
            .map(sa -> sa
                .flatMapToLong(fget))));
  }

}