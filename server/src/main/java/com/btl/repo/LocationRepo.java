package com.btl.repo;

import com.btl.entity.Stored;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<Stored, Long> {

    Stored findByLocationName(String locationName);

    Stored findByLocationType(String locationType);
}
