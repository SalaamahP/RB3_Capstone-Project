package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Student;
import za.ac.cput.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    private static IStudentService service;

    StudentRepository repository;
    @Autowired StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
       return this.repository.save(student);
    }

    @Override
    public Student read(Long id) {
       return this.repository.findById(id).orElse(null);
    }

    @Override
    public Student update(Student student) {
      return this.repository.save(student);
    }

    @Override
    public boolean delete(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public List<Student> getAll() {
        return this.repository.findAll();
    }
}
