package app.services;

import app.model.RegisterForm;
import app.model.User;
import app.model.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CustomUserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User registerNewAccount(RegisterForm rf) throws Exception {
        User u = new User();
        u.setId(new ObjectId());
        u.setUsername(rf.getUsername());
        u.setEmail(rf.getEmail());
        String encoded = encoder.encode(rf.getPassword());
        u.setPassword(encoded);
        u.setStatus(0);
        u.setPoint(new GeoJsonPoint(0,0));
        System.out.println(encoded);
        return repository.save(u);
    }
}
