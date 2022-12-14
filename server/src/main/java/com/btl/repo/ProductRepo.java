package com.btl.repo;

import com.btl.entity.Products;
import com.btl.entity.Stored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Products, Long> {

    Products findByProductId(long id);
    Optional<Products> findByProductCategoryId(int categoryId);

    Optional<Products> findByProductName(String name);

    //Optional<Products> findByLocation(int locationId);

    Products findByProductSku(String SKU);
}
