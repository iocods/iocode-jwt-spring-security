package iocode.web.app.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, String> {

  Optional<User> findByUsername(String username);
}
