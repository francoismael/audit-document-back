package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewJpa extends JpaRepository<Interview, Long> {
}