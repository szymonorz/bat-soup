package app.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findUsersByLogin(String login);
    public User findUserByEmailAndPassword(String email, String password);
    public User findUserByLogin(String login);
}
