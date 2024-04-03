package com.nulp.mobilepartsshop.core.service.part;

import com.nulp.mobilepartsshop.api.v1.part.service.PartTypeService;
import com.nulp.mobilepartsshop.core.entity.part.PartType;
import com.nulp.mobilepartsshop.core.repository.part.PartTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartTypeServiceImpl implements PartTypeService {

    private final PartTypeRepository partTypeRepository;

    @Override
    public List<PartType> getAllPartTypes() {
        return partTypeRepository.findAll();
    }

    @Override
    public Optional<PartType> getPartTypeById(Long id) {
        return partTypeRepository.findById(id);
    }

    @Override
    public PartType createPartType(PartType partType) {
        return partTypeRepository.save(partType);
    }

    @Override
    public Optional<PartType> updatePartType(Long id, PartType partType) {
        return partTypeRepository.findById(id)
                .map(existingPartType -> {
                    partType.setId(id);
                    return partTypeRepository.save(partType);
                });
    }

    @Override
    public boolean deletePartType(Long id) {
        if (partTypeRepository.existsById(id)) {
            partTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
