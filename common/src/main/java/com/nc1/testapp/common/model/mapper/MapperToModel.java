package com.nc1.testapp.common.model.mapper;

public interface MapperToModel<V, T> {
    V toModel(T t);
}
