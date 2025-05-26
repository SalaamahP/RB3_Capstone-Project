package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Student;
import za.ac.cput.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Student create(@RequestBody Student student) {
        return service.create(student);

    }

    @GetMapping("/read/{id}")
    public Student read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update")
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/getall")
    public List<Student> getAll() {
        return service.getAll();
    }
}
