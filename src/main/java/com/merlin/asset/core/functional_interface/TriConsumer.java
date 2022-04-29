package com.merlin.asset.core.functional_interface;

import java.util.function.BiConsumer;

/**
 * @author Minh-Luan H. Phan
 * Created on: 2022.04.08 23:05
 */
public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

}
