package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepository;


@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {
	@Autowired
	private ActorRepository ActorRepo;

	// return all Actor
	@GetMapping("/")
	public JsonResponse listActor() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(ActorRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}

	/// demo path variable return one Actor for given id
	@GetMapping("/{id}")
	public JsonResponse getActor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(ActorRepo.findById(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}

	

	/// adds a new Actor

	@PostMapping("/")
	public JsonResponse addActor(@RequestBody Actor a) {
		// add a new Actor
		JsonResponse jr = null;

		try {
			jr = JsonResponse.getInstance(ActorRepo.save(a));
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		} 
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	/// Update a Actor

	@PutMapping("/")
	public JsonResponse updateActor(@RequestBody Actor a) {
		// update a Actor
		JsonResponse jr = null;
		try {
			if (ActorRepo.existsById(a.getId())) {
				jr = JsonResponse.getInstance(ActorRepo.save(a));

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error updating Actor. id :" + a.getId() + "doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;

	}
	/// delete a Actor

	@DeleteMapping("/{id}")
	public JsonResponse deleteActor(@PathVariable int id) {
		// delete a Actor
		JsonResponse jr = null;
		try {
			if (ActorRepo.existsById(id)) {
				ActorRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful");

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error Deleting Actor. id :" + id + "doesn't exist!");
			}

		}catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}

		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

}
