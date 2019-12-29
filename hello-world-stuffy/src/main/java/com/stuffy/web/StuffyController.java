package com.stuffy.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stuffy.bussiness.Stuffy;
import com.stuffy.db.StuffyRepository;

@CrossOrigin
@RestController
@RequestMapping("/stuffies")
public class StuffyController {
	@Autowired
	private StuffyRepository stuffyRepo;

	// return all stuffies
	@GetMapping("/")
	public JsonResponse listStuffies() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	/// demo path variable return one stuffy for given id
	@GetMapping("/{id}")
	public JsonResponse getStuffy(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findById(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	// demo of request Parameters
	// url =
	// http://localhost:8080/stuffies?id=10&type=fish&color=pink&size=small&limbs=0
//	@GetMapping("")
//	public Stuffy createAStuffy(@RequestParam int id, @RequestParam String type,@RequestParam String color,@RequestParam String size,@RequestParam int limbs) {
//		Stuffy s = new Stuffy(id, type, color, size, limbs);
//		return s;
//	}

	/// adds a new stuffy

	@PostMapping("/")
	public JsonResponse addStuffy(@RequestBody Stuffy s) {
		// add a new stuffy
		JsonResponse jr = null;

		try {
			jr = JsonResponse.getInstance(stuffyRepo.save(s));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	/// Update a stuffy

	@PutMapping("/")
	public JsonResponse updateStuffy(@RequestBody Stuffy s) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (stuffyRepo.existsById(s.getId())) {
				jr = JsonResponse.getInstance(stuffyRepo.save(s));

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error updating Stuffy. id :" + s.getId() + "doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
	/// delete a stuffy

	@DeleteMapping("/{id}")
	public JsonResponse deleteStuffy(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		try {
			if (stuffyRepo.existsById(id)) {
				stuffyRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful");

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error Deleting Stuffy. id :" + id + "doesn't exist!");
			}

		}

		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
}
