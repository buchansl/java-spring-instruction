package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;
@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {
	@Autowired
	private VendorRepository vendorRepo;

// return allVendors
@GetMapping("/")
public JsonResponse listvendor() {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(vendorRepo.findAll());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;
}

/// demo path variable return oneVendor for given id
@GetMapping("/{id}")
public JsonResponse getvendor(@PathVariable int id) {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(vendorRepo.findById(id));
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;
}



/// adds a newVendor

@PostMapping("/")
public JsonResponse addvendor(@RequestBody Vendor v) {
	// add a newVendor
	JsonResponse jr = null;

	try {
		jr = JsonResponse.getInstance(vendorRepo.save(v));
	}catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;

}
/// Update aVendor

@PutMapping("/")
public JsonResponse updatevendor(@RequestBody Vendor v) {
	// update aVendor
	JsonResponse jr = null;
	try {
		if (vendorRepo.existsById(v.getId())) {
			jr = JsonResponse.getInstance(vendorRepo.save(v));

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error updatingVendor. id :" + v.getId() + "doesn't exist!");
		}
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;

}
/// delete aVendor

@DeleteMapping("/{id}")
public JsonResponse deletevendor(@PathVariable int id) {
	// delete aVendor
	JsonResponse jr = null;
	try {
		if (vendorRepo.existsById(id)) {
			vendorRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful");

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error DeletingVendor. id :" + id + "doesn't exist!");
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
