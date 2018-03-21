package com.codingdojo.javabeltexam.services;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingdojo.javabeltexam.models.Event;
import com.codingdojo.javabeltexam.models.User;
import com.codingdojo.javabeltexam.repositories.EventRepository;

@Transactional
@Service
public class EventService {
	private EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public void saveEvent(Event event) {
		eventRepository.save(event);
	}
	
	public List<Object[]> findEventsInState (String state) {
		return eventRepository.findEventsInState(state);
	}
	
	public List<Object[]> findEventsOutOfState (String state) {
		return eventRepository.findEventsOutOfState(state);
	}
	
	public Event findEventById (Long id) {
		return eventRepository.findOne(id);
	}
	
	public void deleteEventById (Long id) {
		Event event = eventRepository.findOne(id);
		eventRepository.delete(event);
	}
	
	public void userJoinEvent(User user, Long id) {
		Event event = eventRepository.findOne(id);
		List<User> attendees = event.getUsers();
		attendees.add(user);
		eventRepository.save(event);
	}
	
	public void cancelUserAttendance(User user, Long id) {
		Event event = eventRepository.findOne(id);
		List<User> attendees = event.getUsers();
		attendees.remove(user);
		eventRepository.save(event);
	}
	
	public void updateEvent(Event event) {
		eventRepository.updateEventDetails(event.getName(), event.getDate(), event.getCity(), event.getState(), event.getId());
	}

}
