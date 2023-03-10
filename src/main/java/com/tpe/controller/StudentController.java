package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.repository.StudentRepository;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    Logger logger=LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    // !!! get all students - butun ogrenciler gelsin
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')") // hasRole den dolayi ROLE_ADMIN yazmamiza gerek kalmadi.
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

    // id ile ogrenci getirelim @RequestParam ile
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // id ile ogrenci getirelim @PathVariable ile
    @GetMapping("{id}") // http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student= studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    // !!! delete
    @DeleteMapping("/{id}") // http://localhost:8080/students/1
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String, String> map=new HashMap<>();
        map.put("message", "Student is deleted successfuly");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.OK); // return ResponseEntity.ok(map); // ayni sey
    }

    // !!! update
    @PutMapping("{id}") // http://localhost:8080/students/1
    public ResponseEntity<Map<String, String>> updateStudent(
            @PathVariable("id") Long id,@Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);
        Map<String, String> map=new HashMap<>();
        map.put("message", "Student is updated successfuly");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // hangi page gonderilecek
            @RequestParam("size") int size, // page basi kac student olacak
            @RequestParam("sort") String prop, // siralama hangi fielda gore yapilacak
            @RequestParam("direction")Sort.Direction direction // dogal sirali mi olsun
            ){
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentPage=studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);
    }

    // !!! get by lastname
    @GetMapping("/querylastname") // http://localhost:8080/students/querylastname
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName){
        List<Student> list=studentService.findStudent(lastName);
        return ResponseEntity.ok(list);
    }

    // !!! get all student by grade (JPQL) java persistence query language
    @GetMapping("/grade/{grade}") // http://localhost:8080/students/grade/75
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade){
        List<Student> list= studentService.findAllEqualsGrade(grade);
        return ResponseEntity.ok(list);
    }

    // !!! db den direk DTO olarak data alabilir miyim?
    @GetMapping("/query/dto") // http://localhost:8080/students/query/dto?id=1
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){
        StudentDTO studentDTO=studentService.findStudentDTOById(id);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping("/welcome") // http://localhost:8080/students/welcome
    public String welcome(HttpServletRequest request){ // HttpServletRequest ile request e ulastim
        logger.warn("-----------------Welcome {}", request.getServletPath());
        return "Student Controller a Hos Geldiniz...";
    }

}
