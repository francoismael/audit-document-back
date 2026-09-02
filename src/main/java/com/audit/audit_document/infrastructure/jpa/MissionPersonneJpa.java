package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.MissionPersonne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionPersonneJpa
        extends JpaRepository<MissionPersonne, Long> {
}