package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientRequestDTO;
import tn.esprit.autoloc.dto.ClientResponseDTO;

import java.util.List;

/**
 * Mapper manuel Client <-> DTOs.
 */
@Component
public class ClientMapper {

    /** Convertit les données reçues en entité (associé à un id null pour la création). */
    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setNom(dto.nom());
        client.setPrenom(dto.prenom());
        client.setEmail(dto.email());
        client.setTelephone(dto.telephone());
        client.setNumPermis(dto.numPermis());
        client.setDateInscription(dto.dateInscription());
        return client;
    }

    /** Expose un client en DTO de réponse (sans entité ni réservations). */
    public ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription()
        );
    }

    public List<ClientResponseDTO> toResponseDTOList(List<Client> clients) {
        return clients.stream().map(this::toResponseDTO).toList();
    }

    /** Reporte les champs modifiables du DTO sur l'entité existante (mise à jour). */
    public void updateEntityFromDTO(Client entity, ClientRequestDTO dto) {
        entity.setNom(dto.nom());
        entity.setPrenom(dto.prenom());
        entity.setEmail(dto.email());
        entity.setTelephone(dto.telephone());
        entity.setNumPermis(dto.numPermis());
        entity.setDateInscription(dto.dateInscription());
    }
}
