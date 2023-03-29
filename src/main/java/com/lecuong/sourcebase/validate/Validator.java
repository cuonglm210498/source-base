package com.lecuong.sourcebase.validate;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author CuongLM18
 * @created 29/03/2023 - 4:05 PM
 * @project source-base
 */
public class Validator<T> {

    private T data;

    public Validator(T data) {
        this.data = data;
    }

    public static <T> Validator<T> data(T data) {
        return new Validator<>(data);
    }

    public <R> Validator<T> validate(Function<T, R> map, Predicate<R> filter, Supplier<? extends RuntimeException> exception) {
        if (filter.test(map.apply(data)) == true) {
            throw exception.get();
        }

        return this;
    }

    public T getData() {
        return data;
    }
}
