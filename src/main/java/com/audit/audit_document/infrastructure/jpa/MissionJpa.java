package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionJpa
        extends JpaRepository<Mission, Long> {
}