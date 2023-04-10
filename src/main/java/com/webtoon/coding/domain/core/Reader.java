package com.webtoon.coding.domain.core;

import java.util.Optional;

public interface Reader<T> {

    T get(Long id);
}
