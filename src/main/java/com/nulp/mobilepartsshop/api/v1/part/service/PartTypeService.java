package com.nulp.mobilepartsshop.api.v1.part.service;

import com.nulp.mobilepartsshop.core.entity.part.PartType;

import java.util.List;
import java.util.Optional;

public interface PartTypeService {

    List<PartType> getAllPartTypes();

    Optional<PartType> getPartTypeById(Long id);

    PartType createPartType(PartType partType);

    Optional<PartType> updatePartType(Long id, PartType partType);

    boolean deletePartType(Long id);
}
