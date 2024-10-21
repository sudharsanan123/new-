package com.example.spring.controller;

import com.example.spring.model.Student;
import com.example.spring.model.Teacher;
import com.example.spring.service.ManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management")
@PreAuthorize("hasRole('MANAGEMENT')") // Restrict access to MANAGEMENT role for all methods
public class ManagementController {

    private final ManagementService managementService;
    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    // Teacher Endpoints
    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        logger.info("Fetching all teachers");
        List<Teacher> teachers = managementService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        logger.info("Adding teacher: {}", teacher.getUsername());
        Teacher createdTeacher = managementService.addTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacherDetails) {
        logger.info("Updating teacher with ID: {}", id);
        Teacher updatedTeacher = managementService.updateTeacher(id, teacherDetails);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int id) {
        logger.info("Deleting teacher with ID: {}", id);
        managementService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/registerTeacher")
    public ResponseEntity<String> registerTeacher(@RequestBody Teacher teacher) {
        try {
            managementService.addTeacher(teacher);
            logger.info("Teacher registered successfully: {}", teacher.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body("Teacher registered successfully.");
        } catch (RuntimeException e) {
            logger.error("Error registering teacher: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Student Endpoints

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = managementService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        logger.info("Adding student: {}", student.getName());
        Student createdStudent = managementService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        logger.info("Updating student with ID: {}", id);
        Student updatedStudent = managementService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        logger.info("Deleting student with ID: {}", id);
        managementService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
