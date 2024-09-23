package com.nc1.testapp.common.model.mapper;

public interface MapperToDto<U, V> {
    U toDto(V v);
}
