package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

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
public class IntStreamView<A> extends View<A, IntStream>
{

  public IntStreamView(Function<A, IntStream> fget)
  {
    super(fget);
  }

  public <C> IntStreamView<C> compose(final View<C, A> that)
  {
    return new IntStreamView<C>(c -> get(that.get(c)));
  }

  public <C> IntStreamView<C> composeFlatMap(final OptionalView<C, A> that)
  {
    return new IntStreamView<C>(c -> {
      Optional<A> oa = that.get(c);
      return oa.isPresent()?get(oa.get()):IntStream.empty();
    });
  }

  public <C> IntStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new IntStreamView<C>(c -> that.get(c).flatMapToInt(fget));
  }

  public <C> OptionalIntStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalIntStreamView<C>(c -> c
        .flatMap(cc -> that.get(cc)
            .map(sa -> sa
                .flatMapToInt(fget))));
  }

}