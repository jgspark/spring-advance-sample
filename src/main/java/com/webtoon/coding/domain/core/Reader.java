package com.webtoon.coding.domain.core;

import java.util.Optional;

public interface Reader<D> {

    D get(Long id);

    <T> Optional<T> get(Long id, Class<T> type);
}
