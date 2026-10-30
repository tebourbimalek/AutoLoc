package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.ReservationRequestDTO;
import tn.esprit.autoloc.dto.ReservationResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Component
public class ReservationMapper {

    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;

    public ReservationMapper(IClientRepository clientRepository, IVehiculeRepository vehiculeRepository) {
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
    }

    public Reservation toEntity(ReservationRequestDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());
        reservation.setStatut(dto.statut());
        appliquerRelations(reservation, dto);
        return reservation;
    }

    public ReservationResponseDTO toResponseDTO(Reservation reservation) {
        Long clientId = reservation.getClient() == null ? null : reservation.getClient().getIdClient();
        Long vehiculeId = reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule();
        Long contratId = reservation.getContrat() == null ? null : reservation.getContrat().getIdContrat();
        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                clientId,
                vehiculeId,
                contratId
        );
    }

    public List<ReservationResponseDTO> toResponseDTOList(List<Reservation> reservations) {
        return reservations.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Reservation entity, ReservationRequestDTO dto) {
        entity.setDateDebut(dto.dateDebut());
        entity.setDateFin(dto.dateFin());
        entity.setStatut(dto.statut());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Reservation reservation, ReservationRequestDTO dto) {
        if (dto.clientId() != null) {
            Client client = clientRepository.findById(dto.clientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", dto.clientId()));
            reservation.setClient(client);
        } else {
            reservation.setClient(null);
        }
        if (dto.vehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(dto.vehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId()));
            reservation.setVehicule(vehicule);
        } else {
            reservation.setVehicule(null);
        }
    }
}