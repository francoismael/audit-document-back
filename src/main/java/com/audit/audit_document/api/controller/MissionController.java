package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.CreateMissionUseCase;
import com.audit.audit_document.application.usecases.GetMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final CreateMissionUseCase createMissionUseCase;
    private final GetMissionUseCase getMissionUseCase;

    public MissionController(
            CreateMissionUseCase createMissionUseCase,
            GetMissionUseCase getMissionUseCase) {

        this.createMissionUseCase = createMissionUseCase;
        this.getMissionUseCase = getMissionUseCase;
    }

    @PostMapping
    public ResponseEntity<Mission> create(
            @RequestBody CreateMissionRequest request) {

        Mission mission = createMissionUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionResponse> getById(
            @PathVariable Long id) {

        MissionResponse mission = getMissionUseCase.execute(id);

        return ResponseEntity.ok(mission);
    }
}