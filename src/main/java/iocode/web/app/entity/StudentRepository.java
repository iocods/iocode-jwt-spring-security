package iocode.web.app.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StudentRepository extends CrudRepository<Student, Long> {

  List<Student> findAll();
}
