package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientRequestDTO;
import tn.esprit.autoloc.dto.ClientResponseDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO create(ClientRequestDTO client) {
        Client clientToSave = clientMapper.toEntity(client);
        if (clientToSave.getIdClient() != null) {
            throw new IllegalArgumentException("L'identifiant du client doit être null lors de la création");
        }
        valider(client);
        return clientMapper.toResponseDTO(clientRepository.save(clientToSave));
    }

    @Override
    public ClientResponseDTO findById(Long id) {
        return clientMapper.toResponseDTO(findEntity(id));
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        return clientMapper.toResponseDTOList(clientRepository.findAll());
    }

    @Override
    public ClientResponseDTO update(Long id, ClientRequestDTO client) {
        Client existing = findEntity(id);
        valider(client);
        clientMapper.updateEntityFromDTO(existing, client);
        return clientMapper.toResponseDTO(clientRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client findEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    private void valider(ClientRequestDTO client) {
        if (client.nom() == null || client.nom().isBlank()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (client.email() == null || client.email().isBlank()) {
            throw new IllegalArgumentException("L'email du client est obligatoire");
        }
    }
}
