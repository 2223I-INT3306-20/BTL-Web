package com.btl.repo;

import com.btl.entity.Sold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SoldRepo extends JpaRepository<Sold, Long> {

    Sold findByProductId(long id);

    Optional<Sold> findBySoldDate(Date date);

    Optional<Sold> findBySoldPrice(String price);


}
