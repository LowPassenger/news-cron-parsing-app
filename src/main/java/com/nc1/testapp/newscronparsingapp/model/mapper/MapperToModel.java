package com.nc1.testapp.newscronparsingapp.model.mapper;

public interface MapperToModel<V, T> {
    V toModel(T t);
}
