package com.notes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notes.entity.Notes;
import com.notes.repository.InterfaceRepository;

@RestController
@CrossOrigin(origins = {"*"})
public class ControllerClass {
  
	@Autowired
	private InterfaceRepository rep;
	
	@GetMapping(path = "/notes")
	public List<Notes> getData()
	{
		return rep.findAll();
	}
	
	@DeleteMapping(path = "/notes/{id}")
	public ResponseEntity<String> deletedata(@PathVariable int id) {
		if(rep.existsById(id)) {
			rep.deleteById(id);
			return ResponseEntity.ok("Delete Successfully");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("record is not found");
		}
	}
	
	@PutMapping("/notes/{id}")
	public ResponseEntity<Notes> updateNote(@PathVariable int id, @RequestBody Notes updatedNote) {
	    Optional<Notes> existingNote = rep.findById(id);
	    
	    if (existingNote.isPresent()) {
	        Notes note = existingNote.get();
	        
	        // Update the fields
	        note.setDescription(updatedNote.getDescription());
	        note.setSubject(updatedNote.getSubject());
	        // update other fields as needed
	        
	        // Save the updated entity
	        Notes savedNote = rep.save(note);
	        return ResponseEntity.ok(savedNote);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping(path = "/notes")
	public ResponseEntity<Notes> sendData(@RequestBody Notes sendData) {
	 try {	
		 Notes saveNote=rep.save(sendData);
		 return ResponseEntity.status(HttpStatus.CREATED).body(saveNote);
	 }
	 catch(Exception e) {
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	 }
	}
}
