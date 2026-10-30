package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratRequestDTO;
import tn.esprit.autoloc.dto.ContratResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.util.List;

@Component
public class ContratMapper {

    private final IReservationRepository reservationRepository;

    public ContratMapper(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Contrat toEntity(ContratRequestDTO dto) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());
        appliquerRelations(contrat, dto);
        return contrat;
    }

    public ContratResponseDTO toResponseDTO(Contrat contrat) {
        Long reservationId = contrat.getReservation() == null ? null : contrat.getReservation().getIdReservation();
        List<Long> paiementIds = contrat.getPaiements() == null ? List.of()
                : contrat.getPaiements().stream().map(p -> p.getIdPaiement()).toList();
        return new ContratResponseDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide(),
                reservationId,
                paiementIds
        );
    }

    public List<ContratResponseDTO> toResponseDTOList(List<Contrat> contrats) {
        return contrats.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Contrat entity, ContratRequestDTO dto) {
        entity.setDateSignature(dto.dateSignature());
        entity.setMontantTotal(dto.montantTotal());
        entity.setValide(dto.valide());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Contrat contrat, ContratRequestDTO dto) {
        if (dto.reservationId() != null) {
            Reservation reservation = reservationRepository.findById(dto.reservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", dto.reservationId()));
            contrat.setReservation(reservation);
        } else {
            contrat.setReservation(null);
        }
    }
}