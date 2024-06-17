package com.kaktooth.manageevents.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonRepository<T, ID>
    extends CrudRepository<T, ID>, PagingAndSortingRepository<T, ID> {
}
