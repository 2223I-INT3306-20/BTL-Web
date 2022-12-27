package com.btl.repo;

import com.btl.entity.Options;
import com.btl.entity.Products;
import com.btl.entity.Stored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepo extends JpaRepository<Products, Long> {

    Products findByProductId(long id);
    Optional<Products> findByProductName(String name);

    Products findByProductSku(String SKU);

    List<Products> findByProductMfg(Date date);

    Products findByOption(Options options);



}
