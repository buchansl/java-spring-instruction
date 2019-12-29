package com.prs.web;

//import java.util.Scanner;

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

import com.prs.business.User;
import com.prs.db.UsersRepository;
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UsersRepository userRepo;

// return all users
@GetMapping("/")
public JsonResponse listuser() {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(userRepo.findAll());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;
}

/// demo path variable return one user for given id
@GetMapping("/{id}")
public JsonResponse getuser(@PathVariable int id) {
	JsonResponse jr = null;
	try {
		jr = JsonResponse.getInstance(userRepo.findById(id));
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;
}



/// adds a new user

@PostMapping("")
public JsonResponse adduser(@RequestBody User u) {
	// add a new user
	JsonResponse jr = null;

	try {
		jr = JsonResponse.getInstance(userRepo.save(u));
	}catch (DataIntegrityViolationException dive) {
		jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
		e.printStackTrace();
	}
	return jr;

}
/// Update a user

@PutMapping("/")
public JsonResponse updateuser(@RequestBody User u) {
	// update a user
	JsonResponse jr = null;
	try {
		if (userRepo.existsById(u.getId())) {
			jr = JsonResponse.getInstance(userRepo.save(u));

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error updating user. id :" + u.getId() + "doesn't exist!");
		}
	} catch (Exception e) {
		jr = JsonResponse.getInstance(e);
	}
	return jr;

}
/// delete a user

@DeleteMapping("/{id}")
public JsonResponse deleteuser(@PathVariable int id) {
	// delete a user
	JsonResponse jr = null;
	try {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful");

		} else {
			// doesn't exist
			jr = JsonResponse.getInstance("Error Deleting user. id :" + id + "doesn't exist!");
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
@PostMapping("/login")
public JsonResponse login(@RequestBody User u) {
    JsonResponse jr = null;
    
    try {
        jr = JsonResponse.getInstance(userRepo.findByUserNameAndPassword(u.getUserName(), u.getPassword()));
        }
        catch (Exception e) {
            jr = JsonResponse.getInstance(e);
         
            e.printStackTrace();
        }
        return jr;

}

}

//	JsonResponse jr = null;
//
//	String username, password;
//	Scanner s = new Scanner(System.in);
//	System.out.print("Enter username:");// username:user
//	username = s.nextLine();
//	System.out.print("Enter password:");// password:user
//	password = s.nextLine();
//	if (username.equals(username) && password.equals(password)) {
//		System.out.println("Authentication Successful");
//	} else {
//		System.out.println("Authentication Failed");
//	}
//	return jr;