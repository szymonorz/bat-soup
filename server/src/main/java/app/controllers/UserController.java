package app.controllers;

import app.jwt.JwtResponse;
import app.jwt.JwtUtil;
import app.model.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findXAndY()
    {
        List<User> users = userRepository.findUsersByLocationNear(new GeoJsonPoint(163, -89), new Distance(300, Metrics.KILOMETERS));
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @RequestMapping(value = "loginAfterRegister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loginAfterRegister(@RequestAttribute("loginForm") LoginForm loginForm)
    {
        System.out.println("chuj");
        try {
            User user = userRepository.findUserByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            System.out.println(token);
            return ResponseEntity.ok(new JwtResponse(token));
        }catch (NullPointerException e)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "loginUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm)
    {
        System.out.println("chuj");
        try {
            User user = userRepository.findUserByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            System.out.println(token);
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.add("Authorization","Bearer "+token);

            return ResponseEntity.ok(new JwtResponse(token));
        }catch (NullPointerException e)
        {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    private ModelAndView createUser(@RequestBody RegisterForm registerForm)
    {
        User u = new User(new ObjectId(), registerForm.getUsername(),registerForm.getPassword(), registerForm.getEmail(), 0, new GeoJsonPoint(0,0));
        try{
            userRepository.save(u);
        }catch (Exception e)
        {
            e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("forward:");
            return modelAndView;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("loginForm",new LoginForm(registerForm.getEmail(),registerForm.getPassword()));
        ModelAndView mav = new ModelAndView("forward:loginAfterRegister", model);
        return mav;
    }
}
