package org.emma.unit5.apimarketemma.service;

import org.emma.unit5.apimarketemma.model.dao.ICategorysDAO;
import org.emma.unit5.apimarketemma.model.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorysService {

    @Autowired
    private ICategorysDAO categorysDAO;

    public List<Category> getAllCategories() {
        return categorysDAO.findAll();
    }

    public List<String> getAllCategoryNames() {
        return categorysDAO.findAllCategoryName();
    };
}
