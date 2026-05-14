package com.beezu.beezu_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.services.DisciplineService;
import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;

@RestController
@RequestMapping("/disciplines")

public class DisciplineController {

    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
   public ResponseEntity<List<DisciplineResponseDTO>> getAll() {
        return ResponseEntity.ok(disciplineService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(disciplineService.findById(id));
    }

    @PostMapping("/{creatorId}")
    public ResponseEntity<DisciplineResponseDTO> create(
        @RequestBody DisciplineRequestDTO dto, 
        @PathVariable Long creatorId
    ){
       DisciplineResponseDTO saved = disciplineService.createDiscipline(dto, creatorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }
}
    

