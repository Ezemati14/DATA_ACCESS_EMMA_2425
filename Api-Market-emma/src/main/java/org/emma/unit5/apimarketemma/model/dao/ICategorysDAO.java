package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategorysDAO extends CrudRepository<Category, Integer> {
}
