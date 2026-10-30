package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ReservationRequestDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;

import java.util.List;

public interface IReservationService {

    ReservationResponseDTO create(ReservationRequestDTO reservation);

    ReservationResponseDTO findById(Long id);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO update(Long id, ReservationRequestDTO reservation);

    void deleteById(Long id);
}