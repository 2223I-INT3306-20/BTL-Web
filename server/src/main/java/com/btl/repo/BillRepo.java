package com.btl.repo;

import com.btl.entity.BillCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepo extends JpaRepository<BillCustomer, Long> {
    BillCustomer findByBatchId(long id);
}
