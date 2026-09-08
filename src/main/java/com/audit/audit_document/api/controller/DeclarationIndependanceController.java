package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateDeclarationIndependanceRequest;
import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.CreateDeclarationIndependanceUseCase;
import com.audit.audit_document.application.usecases.GetDeclarationIndependanceUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/declarations-independance")
public class DeclarationIndependanceController {

    private final CreateDeclarationIndependanceUseCase createUseCase;
    private final GetDeclarationIndependanceUseCase getUseCase;

    public DeclarationIndependanceController(
            CreateDeclarationIndependanceUseCase createUseCase,
            GetDeclarationIndependanceUseCase getUseCase) {

        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
    }

    @PostMapping
    public ResponseEntity<DeclarationIndependanceResponse> create(
            @RequestBody CreateDeclarationIndependanceRequest request) {

        DeclarationIndependanceResponse response =
                createUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeclarationIndependanceResponse> getById(
            @PathVariable Long id) {

        DeclarationIndependanceResponse response =
                getUseCase.execute(id);

        return ResponseEntity.ok(response);
    }
}