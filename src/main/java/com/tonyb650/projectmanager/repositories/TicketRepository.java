package com.tonyb650.projectmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tonyb650.projectmanager.models.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long>{
	List<Ticket> findAll();
}
