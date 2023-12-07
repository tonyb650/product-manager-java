package com.tonyb650.projectmanager.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tonyb650.projectmanager.models.LoginUser;
import com.tonyb650.projectmanager.models.Project;
import com.tonyb650.projectmanager.models.Ticket;
import com.tonyb650.projectmanager.models.User;
import com.tonyb650.projectmanager.services.ProjectService;
import com.tonyb650.projectmanager.services.TicketService;
import com.tonyb650.projectmanager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	@Autowired
	UserService userService;
	@Autowired
	ProjectService projectService;
	@Autowired
	TicketService ticketService;
	
	// PAGE VIEWS
	
	@GetMapping("/")
	public String loginPage(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin",new LoginUser());
		return "loginPage.jsp";
	}
	
	@GetMapping("/home")
	public String dashboard(Model model, HttpSession session) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		User currentUser = userService.getById((Long) session.getAttribute("id"));
		model.addAttribute("currentUserName",session.getAttribute("firstName"));
		model.addAttribute("userProjects", projectService.getUserProjects(currentUser));
		model.addAttribute("nonUserProjects", projectService.getNonUserProjects(currentUser));
		return "dashboard.jsp";
	}
	
	@GetMapping("/projects/new")
	public String newProject(Model model, HttpSession session) {
		// TEST THIS
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		// VS. THIS:     ----> which one works better, add to all get routes
		if(session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Project project = new Project();
		project.setDueDate(new Date());
		model.addAttribute("project", project);
		return "newProject.jsp";
	}
	
	@GetMapping("/projects/{id}/update")
	public String editProject(@PathVariable("id") Long projectId, Model model, HttpSession session) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		Project project = projectService.getById(projectId);
		model.addAttribute("project", project);
		return "editProject.jsp";
	}
	
	@GetMapping("/projects/{id}/show")
	public String showProject(@PathVariable("id") Long projectId, Model model, HttpSession session) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		Project project = projectService.getById(projectId);
		model.addAttribute("project", project);
		return "showProject.jsp";
	}
	
	@GetMapping("/projects/{id}/tickets")
	public String ticketsPage(@PathVariable("id") Long projectId, @ModelAttribute("ticket") Ticket ticket, Model model, HttpSession session) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		Project project = projectService.getById(projectId);
		model.addAttribute("project", project);
		return "ticketsProject.jsp";
	}
	
	// JOIN & LEAVE TEAM (get)
	
	@GetMapping("/projects/{id}/join")
	public String joinProject(@PathVariable("id") Long projectId, HttpSession session ) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		User user = userService.getById((Long) session.getAttribute("id"));
		Project project = projectService.getById(projectId);
		user.getProjects().add(project);
		userService.update(user);
		return "redirect:/home";
	}
	
	@GetMapping("/projects/{id}/leave")
	public String leaveProject(@PathVariable("id") Long projectId, HttpSession session ) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		User user = userService.getById((Long) session.getAttribute("id"));
		Project project = projectService.getById(projectId);
		user.getProjects().remove(project);
		userService.update(user);
		return "redirect:/home";
	}
	
	
	// CREATE PROJECT (post)
	@PostMapping("/projects/create")
	public String createProject(@Valid @ModelAttribute("project") Project project, BindingResult result, HttpSession session) {
		projectService.create(project, result, session);
		if(result.hasErrors()) {
			return "newProject.jsp";
		}
		return "redirect:/home";
	}
	
	// CREATE TICKET
	@PostMapping("/tickets/{id}/new")
	public String createTicket(@PathVariable("id") Long projectId, @Valid @ModelAttribute("ticket") Ticket ticket, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("project", projectService.getById(projectId));
			return "ticketsProject.jsp";
		}
		ticket.setCreator(userService.getById((Long) session.getAttribute("id")));
		ticket.setProject(projectService.getById(projectId));
		ticketService.create(ticket);
		// model.addAttribute("project", projectService.getById(projectId));
		return "redirect:/projects/"+projectId+"/tickets";
	}
	
	// EDIT PROJECT
	@PutMapping("/projects/update")
	public String updateProject(@Valid @ModelAttribute("project") Project project, BindingResult result, HttpSession session) {
		if(!(Boolean) session.getAttribute("isLoggedIn")) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			return "editProject.jsp";
		}
		Project prevProject = projectService.getById(project.getId()); // project.id is coming from hidden input on .jsp form
		project.setTeamLeader(prevProject.getTeamLeader());
		project.setUsers(prevProject.getUsers());
		project.setTickets(prevProject.getTickets());
		projectService.update(project);
		return "redirect:/home";
	}
	
	// DELETE PROJECT
	@DeleteMapping("/projects/delete")
	public String deleteProject(@RequestParam("id")Long projectId) {
		projectService.destroy(projectService.getById(projectId));
		return "redirect:/home";
	}
	
	// REGISTER AND LOGIN (post)
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User user, BindingResult result, HttpSession session, Model model) {
		// ~*~* userService.register -> check if passwords match & check if email already exists, and then saves to DB if all good
		User currentUser = userService.register(user, result);
		if(result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "loginPage.jsp";
		}
		// ~*~* Successfully register new user
		// ~*~* Now set session attributes
		session.setAttribute("firstName", currentUser.getFirstName());
		session.setAttribute("id", currentUser.getId());
		session.setAttribute("isLoggedIn", true);
		return "redirect:/home";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, HttpSession session, Model model) {
		User currentUser = userService.login(newLogin, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser",new User());
			return "loginPage.jsp";
		}
		// ~*~* Successfully logged in
		// ~*~* Now set session attributes
		session.setAttribute("firstName", currentUser.getFirstName());
		session.setAttribute("id", currentUser.getId());
		session.setAttribute("isLoggedIn", true);
		return "redirect:/home";
	}
	
	// LOGOUT
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
