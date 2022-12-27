package com.btl.repo;

import com.btl.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepo extends JpaRepository<Batch, Long> {

    Iterable<Batch> findByFromId(long id);

    boolean existsByFromId(long fromId);

    List<Batch> findByFromIdAndProductId(long fromId, long productId);

    Iterable<Batch> findByToId(long id);

    List<Batch> findByToIdAndProductId(long toId, long productId);

    Iterable<Batch> findByToIdAndFromId(long id, long fromid);
}
