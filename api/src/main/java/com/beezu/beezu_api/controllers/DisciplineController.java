package com.beezu.beezu_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineUpdateDTO;
import com.beezu.beezu_api.services.DisciplineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Discipline",description="Endpoints for managing disciplines")
@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;
    
    public DisciplineController(DisciplineService disciplineService) {
    	this.disciplineService = disciplineService;
    }
	@Operation(summary="Find all disciplines")
    @GetMapping
   public ResponseEntity<List<DisciplineResponseDTO>> getAll() {
        return ResponseEntity.ok(disciplineService.listAll());
    }
	@Operation(summary="Find discipline by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(disciplineService.findById(id));
    }
	@Operation(summary="Create a new discipline")
    @PostMapping
    public ResponseEntity<DisciplineResponseDTO> create(
        @RequestBody @Valid DisciplineRequestDTO dto
    ){
       DisciplineResponseDTO saved = disciplineService.createDiscipline(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
	@Operation(summary="Delete a discipline")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }
    
	@Operation(summary="Update discipline data")
	@PatchMapping("/{id}")
	public ResponseEntity<DisciplineResponseDTO> update(@PathVariable Long id, @RequestBody DisciplineUpdateDTO request){
		DisciplineResponseDTO response =disciplineService.updateDiscipline(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
    

