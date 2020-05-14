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
     public User findUserByEmailAndPassword(String email, String password);
    public User findUserByUsername(String login);
    User findUserByEmail(String email);


    List<User> findUsersByLocationNear(GeoJsonPoint location, Distance distance);


}
