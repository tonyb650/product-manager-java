package com.tonyb650.projectmanager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonyb650.projectmanager.models.Ticket;
import com.tonyb650.projectmanager.repositories.TicketRepository;

@Service
public class TicketService {
	@Autowired
	TicketRepository ticketRepo;
	
	public Ticket getById(Long id) {
		Optional<Ticket> possibleTicket= ticketRepo.findById(id);
		if(possibleTicket.isPresent()) {
			return possibleTicket.get();
		}
		return null;
	}
	
	public Ticket create(Ticket ticket) {
		return ticketRepo.save(ticket);
	}
	
	public void delete(Ticket ticket) {
		ticketRepo.delete(ticket);
	}
}
