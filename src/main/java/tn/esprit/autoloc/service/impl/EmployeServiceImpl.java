package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeRequestDTO;
import tn.esprit.autoloc.dto.EmployeResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeResponseDTO create(EmployeRequestDTO employe) {
        Employe employeToSave = employeMapper.toEntity(employe);
        if (employeToSave.getIdEmploye() != null) {
            throw new IllegalArgumentException("L'identifiant de l'employé doit être null lors de la création");
        }
        return employeMapper.toResponseDTO(employeRepository.save(employeToSave));
    }

    @Override
    public EmployeResponseDTO findById(Long id) {
        return employeMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<EmployeResponseDTO> findAll() {
        return employeMapper.toResponseDTOList(employeRepository.findAll());
    }

    @Override
    public EmployeResponseDTO update(Long id, EmployeRequestDTO employe) {
        Employe existing = findEntity(id);
        employeMapper.updateEntityFromDTO(existing, employe);
        return employeMapper.toResponseDTO(employeRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe findEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }
}