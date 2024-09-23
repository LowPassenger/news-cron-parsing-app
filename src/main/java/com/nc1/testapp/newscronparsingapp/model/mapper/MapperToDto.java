package com.nc1.testapp.newscronparsingapp.model.mapper;

public interface MapperToDto<U, V> {
    U toDto(V v);
}
