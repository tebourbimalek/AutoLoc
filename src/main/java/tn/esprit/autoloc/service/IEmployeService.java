package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.EmployeRequestDTO;
import tn.esprit.autoloc.dto.EmployeResponseDTO;

import java.util.List;

public interface IEmployeService {

    EmployeResponseDTO create(EmployeRequestDTO employe);

    EmployeResponseDTO findById(Long id);

    List<EmployeResponseDTO> findAll();

    EmployeResponseDTO update(Long id, EmployeRequestDTO employe);

    void deleteById(Long id);
}