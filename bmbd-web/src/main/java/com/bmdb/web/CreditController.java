package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepository;

@CrossOrigin
@RestController
@RequestMapping("/credits")

public class CreditController {
	@Autowired
	private CreditRepository CreditRepo;

// return all Credit
@GetMapping("/")
public JsonResponse listCredit() {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(CreditRepo.findAll());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;
}

/// demo path variable return one Credit for given id
@GetMapping("/{id}")
public JsonResponse getCredit(@PathVariable int id) {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(CreditRepo.findById(id));
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;
}



@PostMapping("/")
public JsonResponse addCredit(@RequestBody Credit c) {
	// add a new Credit
	JsonResponse jr = null;

	try {
		jr = JsonResponse.getInstance(CreditRepo.save(c));
	}catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;

}
/// Update a Credit

@PutMapping("/")
public JsonResponse updateCredit(@RequestBody Credit c) {
	// update a Credit
	JsonResponse jr = null;
	try {
		if (CreditRepo.existsById(c.getId())) {
			jr = JsonResponse.getInstance(CreditRepo.save(c));

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error updating Credit. id :" + c.getId() + "doesn't exist!");
		}
	} catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	}catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;

}
/// delete a Credit

@DeleteMapping("/{id}")
public JsonResponse deleteCredit(@PathVariable int id) {
	// delete a Credit
	JsonResponse jr = null;
	try {
		if (CreditRepo.existsById(id)) {
			CreditRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful");

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error Deleting Credit. id :" + id + "doesn't exist!");
		}

	}

	catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;

}

}
