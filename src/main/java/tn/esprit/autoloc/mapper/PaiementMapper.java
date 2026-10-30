package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementRequestDTO;
import tn.esprit.autoloc.dto.PaiementResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

import java.util.List;

@Component
public class PaiementMapper {

    private final IContratRepository contratRepository;

    public PaiementMapper(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    public Paiement toEntity(PaiementRequestDTO dto) {
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.montant());
        paiement.setDatePaiement(dto.datePaiement());
        paiement.setModePaiement(dto.modePaiement());
        appliquerRelations(paiement, dto);
        return paiement;
    }

    public PaiementResponseDTO toResponseDTO(Paiement paiement) {
        Long contratId = paiement.getContrat() == null ? null : paiement.getContrat().getIdContrat();
        return new PaiementResponseDTO(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                contratId
        );
    }

    public List<PaiementResponseDTO> toResponseDTOList(List<Paiement> paiements) {
        return paiements.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntityFromDTO(Paiement entity, PaiementRequestDTO dto) {
        entity.setMontant(dto.montant());
        entity.setDatePaiement(dto.datePaiement());
        entity.setModePaiement(dto.modePaiement());
        appliquerRelations(entity, dto);
    }

    private void appliquerRelations(Paiement paiement, PaiementRequestDTO dto) {
        if (dto.contratId() != null) {
            Contrat contrat = contratRepository.findById(dto.contratId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.contratId()));
            paiement.setContrat(contrat);
        } else {
            paiement.setContrat(null);
        }
    }
}