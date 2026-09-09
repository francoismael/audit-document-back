package com.audit.audit_document.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.audit_document.application.dto.CreatePersonneRequest;
import com.audit.audit_document.application.usecases.CreatePersonneUseCase;
import com.audit.audit_document.application.usecases.GetAllPersonnesUseCase;
import com.audit.audit_document.domain.entity.Personne;

@RestController 
@RequestMapping("api/personnes")
public class PersonneController {
    private final CreatePersonneUseCase createPersonneUseCase;
    private final GetAllPersonnesUseCase getAllPersonnesUseCase;

    public PersonneController(CreatePersonneUseCase createPersonneUseCase,
        GetAllPersonnesUseCase getAllPersonnesUseCase
    ){
        this.createPersonneUseCase = createPersonneUseCase;
        this.getAllPersonnesUseCase = getAllPersonnesUseCase;
    }

    @PostMapping 
    public ResponseEntity<Personne> create(@RequestBody CreatePersonneRequest request){
        Personne personne = createPersonneUseCase.execute(request);
        return ResponseEntity.ok(personne);
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAll() {

    List<Personne> personnes =
            getAllPersonnesUseCase.execute();

    return ResponseEntity.ok(personnes);
    }
}
