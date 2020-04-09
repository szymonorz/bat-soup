package app.model;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Near;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findUsersByUsername(String login);
    public User findUserByEmailAndPassword(String email, String password);
    public User findUserByUsername(String login);
    public User findUserByEmail(String email);

    public List<User> findUsersByLocationWithin(Polygon polygon);


    List<User> findUsersByLocationNear(GeoJsonPoint location, Distance distance);


}
