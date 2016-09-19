package de.c.seiler.simpleoptics.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ObjDoubleFunction<T, R>
{

  R apply(T t, double value);

  default <V> ObjDoubleFunction<T, V> andThen(Function<? super R, ? extends V> after)
  {
    Objects.requireNonNull(after);
    return (T t, double v) -> after.apply(apply(t, v));
  }
}
