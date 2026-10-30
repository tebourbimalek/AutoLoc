package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationRequestDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO create(ReservationRequestDTO reservation) {
        Reservation reservationToSave = reservationMapper.toEntity(reservation);
        if (reservationToSave.getIdReservation() != null) {
            throw new IllegalArgumentException("L'identifiant de la réservation doit être null lors de la création");
        }
        return reservationMapper.toResponseDTO(reservationRepository.save(reservationToSave));
    }

    @Override
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<ReservationResponseDTO> findAll() {
        return reservationMapper.toResponseDTOList(reservationRepository.findAll());
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO reservation) {
        Reservation existing = findEntity(id);
        reservationMapper.updateEntityFromDTO(existing, reservation);
        return reservationMapper.toResponseDTO(reservationRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation findEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }
}