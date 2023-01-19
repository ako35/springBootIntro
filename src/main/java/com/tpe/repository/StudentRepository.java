package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    List<Student> findByLastName(String lastName);

    // !!! JPQL ****************************
    @Query("select s from Student s where s.grade=:pGrade")
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    // Native SQL
    @Query(value = "select * from Student s where s.grade=:pGrade", nativeQuery = true)
    List<Student> findAllEqualsGradeWithSQL(@Param("pGrade") Integer grade);

    @Query("select new com.tpe.dto.StudentDTO(s) from Student s where s.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);
}
