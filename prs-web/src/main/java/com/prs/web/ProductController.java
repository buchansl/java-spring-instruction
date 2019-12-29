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

import com.prs.business.Product;

import com.prs.db.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductRepository productsRepo;

// return all Products
	@GetMapping("/")
	public JsonResponse listProducts() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productsRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();

		}
		return jr;
	}

/// demo path variable return one Products for given id
	@GetMapping("/{id}")
	public JsonResponse getProducts(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productsRepo.findById(id));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

/// adds a new Products

	@PostMapping("/")
	public JsonResponse addProducts(@RequestBody Product p) {
		// add a new Products
		JsonResponse jr = null;

		try {
			jr = JsonResponse.getInstance(productsRepo.save(p));
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;

	}
/// Update a Products

	@PutMapping("/")
	public JsonResponse updateProducts(@RequestBody Product p) {
		// update a Products
		JsonResponse jr = null;
		try {
			if (productsRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(productsRepo.save(p));

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error updating Products. id :" + p.getId() + "doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;

	}
/// delete a Products

	@DeleteMapping("/{id}")
	public JsonResponse deleteProducts(@PathVariable int id) {
		// delete a Products
		JsonResponse jr = null;
		try {
			if (productsRepo.existsById(id)) {
				productsRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful");

			} else {
				// doesn't exist
				jr = JsonResponse.getInstance("Error Deleting Product id :" + id + "doesn't exist!");
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

}
