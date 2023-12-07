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
		// assign 'teamLead' to session user
		Optional<User> possibleUser = userRepository.findById((Long) session.getAttribute("id"));
		if(!possibleUser.isPresent()) { // should never get here
			result.rejectValue("title", "Matches", "Can't assign Team Leader");
		}
		if(result.hasErrors()) {
			return null;
		}
		// ~*~* Set teamLead to currentUser
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
		System.out.println(project.getTitle());
		Project thisProject = projectRepository.save(project);
		System.out.println(thisProject.getTitle());
		return thisProject;
	}
	
	public void destroy(Project project) {
		// need to delete related tickets also
		for(Ticket ticket:project.getTickets()) {
			ticketRepository.delete(ticket);
		}
		projectRepository.delete(project);
	}
	
	
}
