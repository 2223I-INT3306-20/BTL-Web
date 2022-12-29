package com.btl.repo;

import com.btl.entity.Fault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FaultRepo extends JpaRepository<Fault, Long> {

    List<Fault> getFaultByReceiveDate(Date date);

    List<Fault> findByServiceIdAndStatus(long id,String s);

    Fault findByBatchId(long id);
}
