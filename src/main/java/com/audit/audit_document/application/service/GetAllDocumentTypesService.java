package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.usecases.GetAllDocumentTypesUseCase;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllDocumentTypesService
        implements GetAllDocumentTypesUseCase {

    private final DocumentTypeRepository repository;

    public GetAllDocumentTypesService(
            DocumentTypeRepository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeResponse> execute(String query, Boolean actif) {

        List<DocumentTypeResponse> responses =
                new ArrayList<>();

        for (DocumentType type : repository.findAll()) {

            if ((actif != null && !actif.equals(type.getActif()))
                    || !SearchText.matches(query, type.getNom(), type.getCode(), type.getDescription())) {
                continue;
            }

            DocumentTypeResponse response =
                    new DocumentTypeResponse();

            response.setId(type.getId());
            response.setCode(type.getCode());
            response.setNom(type.getNom());
            response.setDescription(
                    type.getDescription());
            response.setActif(type.getActif());
            response.setDateCreation(
                    type.getDateCreation());

            responses.add(response);
        }

        return responses;
    }
}