package com.nulp.mobilepartsshop.api.utils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Mapper<E, R> {

    public abstract R toResponse(E entity);

    public List<R> toResponseList(List<E> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
