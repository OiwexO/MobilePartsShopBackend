package com.nulp.mobilepartsshop.core.repository.part;

import com.nulp.mobilepartsshop.core.entity.partType.PartType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartTypeRepository extends JpaRepository<PartType, Long> {

}
