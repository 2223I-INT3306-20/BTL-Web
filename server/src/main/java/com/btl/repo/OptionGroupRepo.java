package com.btl.repo;

import com.btl.entity.OptionGroup;
import com.btl.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionGroupRepo extends JpaRepository<OptionGroup, Long> {

    OptionGroup findOptionGroupByOptionGroupName(String name);
}
