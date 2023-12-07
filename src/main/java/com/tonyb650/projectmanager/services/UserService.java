package com.tonyb650.projectmanager.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tonyb650.projectmanager.models.LoginUser;
import com.tonyb650.projectmanager.models.User;
import com.tonyb650.projectmanager.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public void create(User user) {
		userRepository.save(user);
	}
	public void update(User user) {
		userRepository.save(user);
	}
	
	public User getById(Long id) {
		Optional<User> possibleUser = userRepository.findById(id);
		if(possibleUser.isPresent()) {
			return possibleUser.get();
		}
		return null;
	}
	
	public User register(User user, BindingResult result) {
		// ~*~* Check if email already exists in DB, then add "rejectValue";
		Optional<User> possibleUser = userRepository.findByEmail(user.getEmail());
		if(possibleUser.isPresent()) {
			result.rejectValue("email", "Matches", "Email is already in use");
		}
		// ~*~* Check if passwords match, if not then add "rejectValue";
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "Matches", "Passwords do not match");
		}
		// ~*~* There are no errors, we can hash PW and save it;
		if(!result.hasErrors()) {
			// ~*~* Use BCrypt.hashpw to generate hashed PW String & then replace original PW with hash
			String hashedPW = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(hashedPW);
			// ~*~* Create user in DB (here in the service, not in the controller)
			return userRepository.save(user);
		}
		return null;
	}
	
	public User login(LoginUser loginUser, BindingResult result) {
		// search for user
		Optional<User> possibleUser = userRepository.findByEmail(loginUser.getEmail());
		if(!possibleUser.isPresent()) {
			result.rejectValue("email", "Matches", "No user found with this email address");
			return null;
		}
		User foundUser = possibleUser.get();
		if(!BCrypt.checkpw(loginUser.getPassword(), foundUser.getPassword())) { // NOTE: the arguments in BCrypt.checkpw CANNOT be reversed
			result.rejectValue("password", "Matches", "Incorrect Password");
			return null;
		}
		return foundUser;
	}
}
