package com.btl.repo;

import com.btl.entity.RequestTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTransferRepo extends JpaRepository<RequestTransfer, Long> {
    RequestTransfer findByRequestId(long request);

    Iterable<RequestTransfer> findByFactoryIdAndStatus(long id, int code);
}
