package de.c.seiler.simpleoptics.primitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.DoubleStream;

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
public class DoubleStreamView<A> extends View<A, DoubleStream>
{

  public DoubleStreamView(Function<A, DoubleStream> fget)
  {
    super(fget);
  }

  public <C> DoubleStreamView<C> compose(final View<C, A> that)
  {
    return new DoubleStreamView<C>(c -> get(that.get(c)));
  }

  public <C> DoubleStreamView<C> composeFlatMap(final OptionalView<C, A> that)
  {
    return new DoubleStreamView<C>(c -> {
      Optional<A> oa = that.get(c);
      return oa.isPresent()?get(oa.get()):DoubleStream.empty();
    });
  }

  public <C> DoubleStreamView<C> composeFlatMap(final StreamView<C, A> that)
  {
    return new DoubleStreamView<C>(c -> that.get(c).flatMapToDouble(fget));
  }
  
  public <C> OptionalDoubleStreamView<C> composeFlatMap(OptionalStreamView<C, A> that)
  {
    return new OptionalDoubleStreamView<C>(c -> c
        .flatMap(cc -> that.get(cc)
            .map(sa -> sa
                .flatMapToDouble(fget))));
  }

}