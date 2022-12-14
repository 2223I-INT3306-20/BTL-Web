package com.btl.repo;

import com.btl.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepo extends JpaRepository<Options, Long> {
    Optional<Options> findByOptionName(String name);

    Options findOptionsByOptionId(long id);
}
