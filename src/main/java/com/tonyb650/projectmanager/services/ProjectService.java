package com.tonyb650.projectmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.tonyb650.projectmanager.models.Project;
import com.tonyb650.projectmanager.models.Ticket;
import com.tonyb650.projectmanager.models.User;
import com.tonyb650.projectmanager.repositories.ProjectRepository;
import com.tonyb650.projectmanager.repositories.TicketRepository;
import com.tonyb650.projectmanager.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	UserService userService;
	
	// FIND METHODS
	public List<Project> getAll(){
		return projectRepository.findAll();
	}
	
	public Project getById(Long id) {
		return projectRepository.findByIdIs(id);
	}
	
	public List<Project> getUserProjects(User user){
		return projectRepository.findAllByUsers(user);
	}
	
	public List<Project> getNonUserProjects(User user){
		return projectRepository.findAllByUsersNotContains(user);
	}
	
	// CRUD METHODS
	public Project create(Project project, BindingResult result, HttpSession session) {
		// ~*~* Retrieve user for 'teamLeader' from session user
		Optional<User> possibleUser = userRepository.findById((Long) session.getAttribute("id"));
		if(!possibleUser.isPresent()) { // should never get here
			result.rejectValue("title", "Matches", "Can't assign Team Leader");
		}
		if(result.hasErrors()) {
			return null;
		}
		// ~*~* Set teamLeader to currentUser
		User teamLeader = possibleUser.get();
		project.setTeamLeader(teamLeader);
		// ~*~* Save project with teamLeader added
		Project thisProject = projectRepository.save(project);
		// ~*~* Add the project to the current user's 'projects'
		teamLeader.getProjects().add(thisProject);
		userRepository.save(teamLeader);
		return thisProject;
	}
	
	public Project update(Project project) {
		Project thisProject = projectRepository.save(project);
		return thisProject;
	}
	
	public void destroy(Project project) {
		// THERE ARE 3 RELATIONSHIPS TO THINK ABOUT WHEN DELETING A PROJECT
		// Perhaps we should remove 'leadingProjects' from user -> BUT, don't need to do anything because this relationship is part of the project that will be deleted from the DB
		// Next, remove this 'project' from any users who are on the team -> This should remove the relationship in the joining table
		for(User user:project.getUsers()) {
			user.getProjects().remove(project);
			userService.update(user);
		}
		// Delete any tickets pertaining to this project
		for(Ticket ticket:project.getTickets()) {
			ticketRepository.delete(ticket);
		}
		// Finally, delete the actual project
		projectRepository.delete(project);
	}
	
	
}
