package app.controllers;

import app.jwt.JwtResponse;
import app.jwt.JwtUtil;
import app.model.*;
import app.services.CustomUserDetailsService;
import app.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        try {
            User user = userRepository.findUserByEmail(loginForm.getEmail());
            CustomUserDetails us = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(us);

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
         try {
            User user = userRepository.findUserByEmail(loginForm.getEmail());
            CustomUserDetails us = (CustomUserDetails) customUserDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtil.generateToken(us);

            return ResponseEntity.ok(new JwtResponse(token));
        }catch (NullPointerException e)
        {
            System.out.println(passwordEncoder.encode(loginForm.getPassword()));
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "getUser/{token}", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<User> getUser(@PathVariable("token") String token)
    {
        String username = jwtUtil.getUsernameFromToken(token);
        User currentUser = userRepository.findUserByUsername(username);
        return ResponseEntity.ok(currentUser);
    }

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    private ModelAndView createUser(@RequestBody RegisterForm registerForm)
    {

        try{
            User u = userService.registerNewAccount(registerForm);
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
