package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Movie;
import com.bmdb.db.MovieRepository;
@CrossOrigin
@RestController
@RequestMapping("/movies")

public class MovieController {	
	@Autowired
	private MovieRepository movieRepo;

// return all movies
@GetMapping("/")
public JsonResponse listMovie() {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(movieRepo.findAll());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;
}

/// demo path variable return one movie for given id
@GetMapping("/{id}")
public JsonResponse getmovie(@PathVariable int id) {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(movieRepo.findById(id));
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;
}



/// adds a new movie

@PostMapping("/")
public JsonResponse addmovie(@RequestBody Movie m) {
	// add a new movie
	JsonResponse jr = null;

	try {
		jr = JsonResponse.getInstance(movieRepo.save(m));
	}catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;

}
/// Update a movie

@PutMapping("/")
public JsonResponse updatemovie(@RequestBody Movie m) {
	// update a movie
	JsonResponse jr = null;
	try {
		if (movieRepo.existsById(m.getId())) {
			jr = JsonResponse.getInstance(movieRepo.save(m));

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error updating movie. id :" + m.getId() + "doesn't exist!");
		}
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;

}
/// delete a movie

@DeleteMapping("/{id}")
public JsonResponse deletemovie(@PathVariable int id) {
	// delete a movie
	JsonResponse jr = null;
	try {
		if (movieRepo.existsById(id)) {
			movieRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful");

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error Deleting movie. id :" + id + "doesn't exist!");
		}

	}catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	}

	catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;

}

}
