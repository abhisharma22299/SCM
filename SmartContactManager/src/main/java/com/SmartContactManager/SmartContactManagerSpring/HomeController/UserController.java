package com.SmartContactManager.SmartContactManagerSpring.HomeController;

import com.SmartContactManager.Helper.Message;
import com.SmartContactManager.SmartContactManagerSpring.Entites.Contact;
import com.SmartContactManager.SmartContactManagerSpring.Entites.User;
import com.SmartContactManager.SmartContactManagerSpring.dao.ContactRepository;
import com.SmartContactManager.SmartContactManagerSpring.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserRepository userRepository;

	@ModelAttribute
	public void addCommonData(Model models, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME" + userName);
		User user = userRepository.getUserByUserName(userName);
		System.out.println("USER" + user);
		models.addAttribute("user", user);

	}

	@RequestMapping("/index")
	public String dashBoard(Model models, Principal principal) {

		return "normal/user_dashboard";
	}

	@GetMapping("/add-contact")
	public String openAddContact(Model models) {
		models.addAttribute("tittle", "Add-Contact");
		models.addAttribute("contact", new Contact());

		return "normal/add_contact_form";
	}


	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,
								 @RequestParam("profileImage") MultipartFile file, Principal principal,
								 HttpSession session) {
		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);
			contact.setUser(user);
			user.getContacts().add(contact);
			if (file.isEmpty()) {
				System.out.println("Img not selected");
				System.out.println();
				contact.setImage("contact.JPG");

				//error messaging
				System.out.println("Image not uploaded");
				session.setAttribute("message", new Message("Your Contact is Added Successfully ! Add More", "success"));
			} else {
				Date date = new Date();

				// display time and date using toString()
				System.out.println(date.toString());

				//uploading file
				System.out.println();
				System.out.println("Else bocj in pricessing image");
				System.out.println();
				contact.setImage(file.getOriginalFilename());
					File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.
						separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image uploaded");
				session.setAttribute("message", new Message("Your Contact is Added Successfully ! Add More", "success"));
			}
			this.userRepository.save(user);
			System.out.println("DATA" + contact);
			System.out.println("Added to data base in contact filed");
		} catch (Exception e) {
			System.out.println();
			System.out.println("catch box bocj in pricessing image");
			System.out.println();
			System.out.println("Error" + e.getMessage());
			e.printStackTrace();
			//message for not success
			session.setAttribute("message", new Message("Something went wrong! Add Again",
					"danger"));

		}

		return "normal/add_contact_form";
	}

	// show contacts handler
// apply pagination 10[n]
	@GetMapping("/show-contact")
	public String showContact(Model model, Principal principal) {

		model.addAttribute("tittle", "Show-Contacts");
		String UserName = principal.getName();
		User user = this.userRepository.getUserByUserName(UserName);
		int userIdDetails = user.getId();

		List<Contact> contacts = this.contactRepository.findContactByUser(user.getId());

		model.addAttribute("contacts", contacts);
		System.out.println("THIS IS USER SHOW CONTACTS");

		return "normal/show_contact";
	}


	// showing specfic contact details
//@RequestMapping("/{CId}/contact}")
//public String showContactDetails(@PathVariable("CId") Integer CId) {
//	System.out.println("CId"+CId);
//	
//	return"normal/contact_details";
//}
	@RequestMapping("/{CId}/contact")
	public String showContactDetailsSpecficUser(@PathVariable("CId") Integer CId, Model models
			, Principal principal) {


		Optional<Contact> contactOptional = this.contactRepository.findById(CId);
		Contact contact = contactOptional.get();

		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		if (user.getId() == contact.getUser().getId()) {
			models.addAttribute("contact", contact);
		}

		return "normal/contact_details";

	}

	// deleting contact
	@GetMapping("/delete/{CId}")
	@Transactional
	public String deleteContact(@PathVariable("CId") Integer CId, Principal principal, HttpSession session) {

		Contact contact = this.contactRepository.findById(CId).get();
		User user = this.userRepository.getUserByUserName(principal.getName());
		user.getContacts().remove(contact);
		//contact.setUser(null);
		this.userRepository.save(user);
		System.out.println("deleted");
		session.setAttribute("message", new Message("Contact deleted Sucessfully", "success"));
		return "redirect:/user/show-contact";
	}
//  open update contact foam

	@PostMapping("/update-contact/{CId}")
	public String updateForm(@PathVariable("CId") Integer CId, Model models) {

		models.addAttribute("tittle", "Update-Contact");

		Contact contact = this.contactRepository.findById(CId).get();
		models.addAttribute("contact", contact);


		return "normal/update_form";

	}
// update contact Handler
//,@RequestParam("profileImage") MultipartFile file
	@PostMapping("/process-update")
	public String updateInContact(@ModelAttribute Contact contact
			,Model model, HttpSession session,Principal principal,@RequestParam("profileImage") MultipartFile file) {//
//		System.out.println("Contact NAME"+contact.getName());
//		System.out.println("Contact number"+contact.getPhone() );
		// old contact details
	Contact oldContactDetails=this.contactRepository.findById(contact.getCId()).get();
	try{
		// rewrite

if (!file.isEmpty()) {
	//delte old photo
File deleteFile=new ClassPathResource("static/img").getFile();
File file1=new File(deleteFile,oldContactDetails.getImage());
file1.delete();



	// update new photo
	File saveFile = new ClassPathResource("static/img").getFile();
	Path path = Paths.get(saveFile.getAbsolutePath() + File.
			separator + file.getOriginalFilename());
	Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	contact.setImage(file.getOriginalFilename());
}//
		else
{
	contact.setImage(oldContactDetails.getImage());
}
// update new photoK
	User user=this.userRepository.getUserByUserName(principal.getName());
		contact.setUser(user);
	this.contactRepository.save(contact);
	contact.setImage("static/img");
	session.setAttribute("message",new Message("Your contact is updated","success"));
}catch (Exception e){
	e.printStackTrace();
}
//		redirect:/user/"+contact.getCId()+"/contact
	return "redirect:/user/"+contact.getCId()+"/contact";
	}
// profile handler
@GetMapping("/profile")
	public String yourProfile(Model model){
model.addAttribute("tittle","Profile Page");


		return "normal/profile";
	}



//	@PostMapping("/{CId}/contact")
//	public String yourProfiles(Model model ,@ModelAttribute Contact contact){
//		model.addAttribute("tittle","Profile Page");
//
//
//		return "redirect:/user/"+contact.getCId()+"/contact";
//	}
//


	// open setting handler
	@GetMapping("/settings")
	public String openSettings(){

		return "normal/settings";
	}

	// change password processing
	@PostMapping("/change-password")
	public String changepasswordprocess(@RequestParam("oldPassword") String oldPassword,
										@RequestParam("newPassword") String newPassword,Principal principal
	                                            ,@RequestParam("confirmPassword")String confirmPassword  ,HttpSession session)
	{

		System.out.println(oldPassword);
		System.out.println(newPassword);
String userName=principal.getName();
User  currentUser=this.userRepository.getUserByUserName(userName);
if(this.bCryptPasswordEncoder.matches(oldPassword,currentUser.getPassword())){

// check from confirm password
if(newPassword.equals(confirmPassword)){
	// change the password
	currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
	this.userRepository.save(currentUser);
	session.setAttribute("message", new Message("Your password is sucessfully changed ", "success"));

}else {

	session.setAttribute("message", new Message("Please enter correct confirm password ", "danger"));
	return "redirect:/user/settings";
}



}

else{
	// invalid password

	session.setAttribute("message", new Message("Please enter correct old password ", "danger"));
	return "redirect:/user/settings";
}

		return "redirect:/user/settings";
	}
	}