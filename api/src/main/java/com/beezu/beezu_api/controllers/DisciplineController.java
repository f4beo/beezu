package com.beezu.beezu_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.repositories.DisciplineRepository;

@RestController
@RequestMapping("/disciplines")

public class DisciplineController {

    @Autowired
    private DisciplineRepository disciplineRepository;

    @GetMapping
    public List<Discipline> getAllDisciplines(){
        return disciplineRepository.findAll();
    }

    @GetMapping("/{id}")
    public Discipline getDisciplineById(@PathVariable Long id){
        return disciplineRepository.findById(id).orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
    }
    
}
