package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.audit.audit_document.domain.entity.DeclarationIndependance;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;
import com.audit.audit_document.infrastructure.jpa.DeclarationIndependanceJpa;

@Repository
public class DeclarationIndependanceAdapter implements DeclarationIndependanceRepository {
    private final DeclarationIndependanceJpa declarationIndependanceJpa;

    public DeclarationIndependanceAdapter( DeclarationIndependanceJpa declarationIndependanceJpa) {
        this.declarationIndependanceJpa = declarationIndependanceJpa;
    }

    @Override 
    public DeclarationIndependance save(DeclarationIndependance declaration){
        return declarationIndependanceJpa.save(declaration);
    }

    @Override 
    public Optional<DeclarationIndependance> findById(Long id){
        return declarationIndependanceJpa.findById(id);
    }

    @Override 
    public List<DeclarationIndependance> findByMissionId(Long missionId){
        return declarationIndependanceJpa.findByMissionId(missionId);
    }

    @Override 
    public List<DeclarationIndependance> findByPersonneId(Long personneId){
        return declarationIndependanceJpa.findByPersonneId(personneId);
    }


    @Override 
    public void deleteById(Long id){
        declarationIndependanceJpa.deleteById(id);
    }
}