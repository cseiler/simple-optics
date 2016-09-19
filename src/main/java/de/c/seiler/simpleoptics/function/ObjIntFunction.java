package de.c.seiler.simpleoptics.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ObjIntFunction<T, R>
{

  R apply(T t, int value);

  default <V> ObjIntFunction<T, V> andThen(Function<? super R, ? extends V> after)
  {
    Objects.requireNonNull(after);
    return (T t, int v) -> after.apply(apply(t, v));
  }
}
