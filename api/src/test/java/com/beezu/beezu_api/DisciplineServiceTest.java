package com.beezu.beezu_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beezu.beezu_api.dtos.DisciplineRequestDTO;
import com.beezu.beezu_api.dtos.DisciplineResponseDTO;
import com.beezu.beezu_api.dtos.DisciplineUpdateDTO;
import com.beezu.beezu_api.exceptions.DisciplineNotFoundException;
import com.beezu.beezu_api.exceptions.UserNotFoundException;
import com.beezu.beezu_api.models.Discipline;
import com.beezu.beezu_api.models.Hive;
import com.beezu.beezu_api.models.User;
import com.beezu.beezu_api.repositories.DisciplineRepository;
import com.beezu.beezu_api.repositories.UserRepository;
import com.beezu.beezu_api.services.DisciplineService;

@ExtendWith(MockitoExtension.class)
class DisciplineServiceTest {

    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DisciplineService disciplineService;

    private User user;
    private Discipline discipline;
    private Hive hive;

    @BeforeEach
    void setup() {

        user = new User();
        user.setName("Arthur");

        hive = new Hive();
        hive.setHealth(100);
        
        discipline = new Discipline();
        discipline.setName("Algorithms");
        discipline.setDescription("Algorithm fundamentals");
        discipline.setProfessor("Dr. Silva");
        discipline.setHive(hive);
        
        
    }

    @Test
    void shouldCreateDisciplineSuccessfully() {

        // Arrange
        DisciplineRequestDTO dto = new DisciplineRequestDTO(
                "Algorithms",
                "Algorithm fundamentals",
                "Dr. Silva"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // Act
        DisciplineResponseDTO response =
                disciplineService.createDiscipline(dto, 1L);

        // Assert
        assertNotNull(response);
        assertEquals("Algorithms", response.name());

        verify(userRepository).findById(1L);
        verify(disciplineRepository).save(any(Discipline.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        // Arrange
        DisciplineRequestDTO dto = new DisciplineRequestDTO(
                "Algorithms",
                "Algorithm fundamentals",
                "Dr. Silva"
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                UserNotFoundException.class,
                () -> disciplineService.createDiscipline(dto, 1L)
        );

        verify(userRepository).findById(1L);

        verify(disciplineRepository, never())
                .save(any());
    }

    @Test
    void shouldFindDisciplineByIdSuccessfully() {

        // Arrange
        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.of(discipline));

        // Act
        DisciplineResponseDTO response =
                disciplineService.findById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Algorithms", response.name());

        verify(disciplineRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDisciplineNotFoundById() {

        // Arrange
        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                DisciplineNotFoundException.class,
                () -> disciplineService.findById(1L)
        );

        verify(disciplineRepository).findById(1L);
    }

    @Test
    void shouldListAllDisciplinesSuccessfully() {

        // Arrange
        when(disciplineRepository.findAll())
                .thenReturn(List.of(discipline));

        // Act
        List<DisciplineResponseDTO> response =
                disciplineService.listAll();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Algorithms", response.getFirst().name());

        verify(disciplineRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoDisciplinesExist() {

        // Arrange
        when(disciplineRepository.findAll())
                .thenReturn(List.of());

        // Act
        List<DisciplineResponseDTO> response =
                disciplineService.listAll();

        // Assert
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(disciplineRepository).findAll();
    }

    @Test
    void shouldUpdateDisciplineSuccessfully() {

        // Arrange
        DisciplineUpdateDTO dto = new DisciplineUpdateDTO(
                "Advanced Algorithms",
                "Updated description",
                "Dr. João"
        );

        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.of(discipline));

        // Act
        DisciplineResponseDTO response =
                disciplineService.updateDiscipline(1L, dto);

        // Assert
        assertNotNull(response);
        assertEquals("Advanced Algorithms", response.name());

        verify(disciplineRepository).findById(1L);
        verify(disciplineRepository).save(discipline);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentDiscipline() {

        // Arrange
        DisciplineUpdateDTO dto = new DisciplineUpdateDTO(
                "Advanced Algorithms",
                "Updated description",
                "Dr. João"
        );

        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                DisciplineNotFoundException.class,
                () -> disciplineService.updateDiscipline(1L, dto)
        );

        verify(disciplineRepository).findById(1L);

        verify(disciplineRepository, never())
                .save(any());
    }

    @Test
    void shouldDeleteDisciplineSuccessfully() {

        // Arrange
        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.of(discipline));

        // Act
        disciplineService.deleteDiscipline(1L);

        // Assert
        verify(disciplineRepository).findById(1L);
        verify(disciplineRepository).delete(discipline);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentDiscipline() {

        // Arrange
        when(disciplineRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                DisciplineNotFoundException.class,
                () -> disciplineService.deleteDiscipline(1L)
        );

        verify(disciplineRepository).findById(1L);

        verify(disciplineRepository, never())
                .delete(any());
    }
}