package com.yn.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {

    T findOne(Integer id);

    void save(T t);

    void delete(Integer id);

    T findOne(T t);

    List<T> findAll(List<Integer> list);

    List<T> findAll(T t);

    Page<T> findAll(T t, Pageable pageable);

	void deleteBatch(List<Integer> ids);
}
