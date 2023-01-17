package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // !!! get all students - butun ogrenciler gelsin
    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        List<Student>students=studentService.getAll();
        return ResponseEntity.ok(students);
    }

    // !!! student objesi olusturalim
    @PostMapping
    public ResponseEntity<Map<String, String>> createStudent(@Valid @RequestBody Student student){
        /*
        @Valid: parametreler valid mi kontrol eder, bu ornekte student objesi olusturmak icin gonderilen field lar
                yani name gibi ozellikler duzgun set edilmis mi ona bakar.
        @RequestBody: gelen parametreyi, request in bodysindeki bilgisi, student objesine map edilmesini sagliyor.
         */
        studentService.createStudent(student);
        Map<String, String> map=new HashMap<>();
        map.put("message", "Student is created successfuly");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    // id ile ogrenci getirelim
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

}
