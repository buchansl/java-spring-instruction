package com.prs.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import com.prs.business.*;
import com.prs.db.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepo;

// return all Requests
	@GetMapping("/")
	public JsonResponse listRequest() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}

/// demo path variable return one Request for given id
	@GetMapping("/{id}")
	public JsonResponse getRequest(@PathVariable int id) {
		JsonResponse jr = null;
		Request r = new Request();
		try {
			jr = JsonResponse.getInstance(requestRepo.findById(id));
			r.getTotal();
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

/// adds a new Request

	@PostMapping("/")
	public JsonResponse addRequest(@RequestBody Request r) {
		// add a new Request
		JsonResponse jr = null;

		try {
			r.setSubmittedDate(LocalDateTime.now());
			r.setStatus("New");
			jr = JsonResponse.getInstance(requestRepo.save(r));
			r.getTotal();

		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;

	}
/// Update a Request

	@PutMapping("/")
	public JsonResponse updateRequest(@RequestBody Request r) {
		// update a Request
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(r.getId())) {
				jr = JsonResponse.getInstance(requestRepo.save(r));
				r.getTotal();
			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error updating Request id :" + r.getId() + "doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
/// delete a Request

	@DeleteMapping("/{id}")
	public JsonResponse deleteRequest(@PathVariable int id) {
		// delete a Request
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful");

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error Deleting Request. id :" + id + "," + "doesn't exist!");
			}

		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		}

		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}

	@PutMapping("/submit-review")
	public JsonResponse submitForReview(@RequestBody Request r) {
		// update a Request
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(r.getId())) {
				if (r.getTotal() <= 50) {
					r.setStatus("Approved");
				} else {
					r.setStatus("Review");
				}
				jr = JsonResponse.getInstance(requestRepo.save(r));

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error submitting Request :" + r.getId());
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}

	@GetMapping("/list-review/{id}")
	public JsonResponse listRequestid(@PathVariable int id) {
		JsonResponse jr = null;
	
		try{
			List<Request> requests = new ArrayList<>();
			
			requests = requestRepo.findAllByStatusAndUserIdNot("Review",id);
			jr = JsonResponse.getInstance(requests);
		}catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		
		return jr;
	}
	@PutMapping("/approve")
	public JsonResponse approve(@RequestBody Request r) {
		// update a Request
		JsonResponse jr = null;
		try { r.setStatus("Approved");
		requestRepo.save(r);
			
			}
		 catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

}
	@PutMapping("/reject")
	public JsonResponse reject(@RequestBody Request r) {
		// update a Request
		JsonResponse jr = null;
		try { r.setStatus("Reject");
		requestRepo.save(r);
			
			}
		 catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

}
}
//@GetMapping("/request/{id}/")
//public JsonResponse getRequests(@PathVariable int id) {
//	JsonResponse jr = null;
//	try {
//		jr = JsonResponse.getInstance(requestRepo.findById(id));
//	} catch (Exception e) {
//		jr = JsonResponse.getInstance(e);
//	}
//	return jr;
//}
