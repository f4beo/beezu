package com.beezu.beezu_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.beezu.beezu_api.dtos.ActivityResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineUpdateDTO;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.mappers.ActivityMapper;
import com.beezu.beezu_api.mappers.DisciplineMapper;
import com.beezu.beezu_api.models.Activity;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.models.UserDiscipline;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;

@Service
public class DisciplineService {
	
	private final DisciplineRepository disciplineRepository;
	private final UserRepository userRepository;

	
	public DisciplineService(DisciplineRepository disciplineRepository, UserRepository userRepository) {
		this.disciplineRepository = disciplineRepository;
		this.userRepository = userRepository;
	}
	
	public DisciplineResponseDTO createDiscipline(DisciplineRequestDTO dto, Long creatorId) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		
		User creator = userRepository.findById(creatorId).orElseThrow(() -> new UserNotFoundException("User not found"));

		Discipline discipline = DisciplineMapper.toEntity(dto);
		
		UserDiscipline enrollment = new UserDiscipline(creator, discipline);
		enrollment.promoteToModerator();
		
		discipline.addEnrollment(enrollment);
		
		disciplineRepository.save(discipline);
		
		return DisciplineMapper.toResponse(discipline);
	}
	
	public DisciplineResponseDTO findById(Long id) {
		Discipline discipline = findEntityById(id);
		return DisciplineMapper.toResponse(discipline);
		
	}
	
	public DisciplineResponseDTO findByCode(String code) {
	   return disciplineRepository.findByDisciplineCode(code).map(DisciplineMapper::toResponse).
			   orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
	}
	
	public List<DisciplineResponseDTO> listAll(){
		List<Discipline> disciplines = disciplineRepository.findAll();	
		return disciplines.stream().map(DisciplineMapper::toResponse).toList();
	}
	
	public List<ActivityResponseDTO> listAllDisciplineActivities(Long disciplineId){
		Discipline discipline = findEntityById(disciplineId);
	    List<Activity> disciplineActivities = discipline.getActivities();
	    
	    return disciplineActivities.stream().map(ActivityMapper::toResponse).toList();
	}
	
	public DisciplineResponseDTO updateDiscipline(Long id, DisciplineUpdateDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Discipline discipline = findEntityById(id);
		updateData(discipline, dto);
		disciplineRepository.save(discipline);
		
		return DisciplineMapper.toResponse(discipline);
	}
	
	public void deleteDiscipline(Long id) {
		Discipline discipline = findEntityById(id);
		disciplineRepository.delete(discipline);
	}
	
    private Discipline findEntityById(Long id) {
    	Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new DisciplineNotFoundException("Discipline not found"));
    	return discipline;
    }
    private void updateData(Discipline entity, DisciplineUpdateDTO dto) {
		entity.setName(dto.name());
		entity.setDescription(dto.description());	
		entity.setProfessor(dto.professor());
	}
	

}
