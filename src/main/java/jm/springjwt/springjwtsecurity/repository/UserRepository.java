package jm.springjwt.springjwtsecurity.repository;


import jm.springjwt.springjwtsecurity.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String username);


    @Override
    @Query("select distinct user from User user join fetch user.roles")
    Iterable<User> findAll();
}
