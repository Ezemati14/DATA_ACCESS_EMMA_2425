package org.emma.unit5.apimarketemma.model.dao;

import org.emma.unit5.apimarketemma.model.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategorysDAO extends CrudRepository<Category, Integer> {

    @Query("SELECT c.categoryName FROM Category c")
    List<String> findAllCategoryName();

    List<Category> findAll();
}
