package de.c.seiler.simpleoptics;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import de.c.seiler.simpleoptics.primitive.DoubleLens;
import de.c.seiler.simpleoptics.primitive.IntLens;
import de.c.seiler.simpleoptics.primitive.LongLens;
import de.c.seiler.simpleoptics.primitive.OptionalDoubleLens;
import de.c.seiler.simpleoptics.primitive.OptionalIntLens;
import de.c.seiler.simpleoptics.primitive.OptionalLongLens;

/**
 * "A lens is basically a getter/setter that can be used for deep updates of immutable data."
 * http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html
 * @param <A>
 * @param <B>
 */
public class OptionalLens<A, B> extends OptionalView<A, B>
{
  public final BiFunction<Optional<A>, Optional<B>, Optional<A>> fset;

  public OptionalLens(Lens<A, B> lens)
  {
    super(oa -> oa.flatMap(a -> Optional.ofNullable(lens.fget.apply(a))));
    this.fset = (oa, ob) -> oa
        .flatMap(a -> ob.isPresent()?ob.map(b -> lens.fset.apply(a, b)):Optional.of(lens.fset.apply(a, null)));
  }

  public OptionalLens(Function<Optional<A>, Optional<B>> fget, BiFunction<Optional<A>, Optional<B>, Optional<A>> fset)
  {
    super(fget);
    this.fset = fset;
  }

  public Optional<A> set(Optional<A> a, Optional<B> b)
  {
    return fset.apply(a, b);
  }

  public Optional<A> set(Optional<A> a, B b)
  {
    return fset.apply(a, Optional.ofNullable(b));
  }

  public Optional<A> set(A a, Optional<B> b)
  {
    return fset.apply(Optional.ofNullable(a), b);
  }

  public Optional<A> set(A a, B b)
  {
    return fset.apply(Optional.ofNullable(a), Optional.ofNullable(b));
  }

  public Optional<A> mod(Optional<A> a, UnaryOperator<Optional<B>> f)
  {
    return set(a, f.apply(get(a)));
  }

  public Optional<A> mod(A a, UnaryOperator<Optional<B>> f)
  {
    return set(a, f.apply(get(a)));
  }

  public <C> OptionalLens<C, B> compose(final Lens<C, A> that)
  {
    return new OptionalLens<C, B>(
        c -> c.flatMap(c1 -> get(that.get(c1))),
        (c, b) -> c.flatMap(c2 -> b.map(b1 -> that.mod(c2, a -> set(a, b1).orElseThrow(NullPointerException::new)))));
  }

  public <C> OptionalLens<C, B> compose(final OptionalLens<C, A> that)
  {
    return new OptionalLens<C, B>(
        c -> get(that.get(c)),
        (c, b) -> that.mod(c, a -> set(a, b)));
  }

  public <C> OptionalLens<A, C> andThen(OptionalLens<B, C> that)
  {
    return that.compose(this);
  }

  public <C> OptionalLens<A, C> andThen(Lens<B, C> that)
  {
    return that.compose(this);
  }

  public OptionalIntLens<A> andThen(OptionalIntLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalIntLens<A> andThen(IntLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalLongLens<A> andThen(OptionalLongLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalLongLens<A> andThen(LongLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalDoubleLens<A> andThen(OptionalDoubleLens<B> that)
  {
    return that.compose(this);
  }

  public OptionalDoubleLens<A> andThen(DoubleLens<B> that)
  {
    return that.compose(this);
  }

}