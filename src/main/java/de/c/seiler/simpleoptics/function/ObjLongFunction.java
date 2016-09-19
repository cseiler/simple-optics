package de.c.seiler.simpleoptics.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ObjLongFunction<T, R>
{

  R apply(T t, long value);

  default <V> ObjLongFunction<T, V> andThen(Function<? super R, ? extends V> after)
  {
    Objects.requireNonNull(after);
    return (T t, long v) -> after.apply(apply(t, v));
  }
}
