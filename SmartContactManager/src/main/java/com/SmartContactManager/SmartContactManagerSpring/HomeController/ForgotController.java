package com.SmartContactManager.SmartContactManagerSpring.HomeController;

import com.SmartContactManager.Helper.Message;
import com.SmartContactManager.SmartContactManagerSpring.Entites.User;
import com.SmartContactManager.SmartContactManagerSpring.dao.UserRepository;
import com.SmartContactManager.SmartContactManagerSpring.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class ForgotController {
//
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
 @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    Random random=new Random(1000);
   private  int otp=random.nextInt(9999999);
// email id form open handler
@RequestMapping("/forgot")
    public String openString(){


        return "forgot_email_form";
    }
// Sending otp handler
    @PostMapping ("/send-otp")
    public String sendOTP(@RequestParam("email") String email , HttpSession session){
        System.out.println("1");
    System.out.println(email);
        // generating otp 4 digit
        System.out.println("2");
        User user=(User)this.userRepository.getUserByUserName(email);

        System.out.println("3");
        System.out.println(otp);
// write code for send otp to email
        System.out.println("4");
        String subject="OTP from SCM";
        String message= STR."\{otp}";
        String to =email;
        System.out.println("5");
        System.out.println("6");

        boolean flag= this.emailService.sendEmail(subject,message,to);

        System.out.println("7");
       if(flag){
           System.out.println("8");
session.setAttribute("email",user.getEmail());

           return "verify_otp";
       }
       else {
           System.out.println("9");
        session.setAttribute("message","check your email id");
           System.out.println("10");
           return  "forgot_email_form";


       }
       }

       //verify-otp
    @PostMapping("/verify-otp")
    public String verify_tp(@RequestParam("votp") int votp,HttpSession session) {

    String email=(String) session.getAttribute("email");
        System.out.println(email);
        if (otp == votp) {
            System.out.println("1");
            //password change

            User user= this.userRepository.getUserByUserName(email);
            System.out.println(this.userRepository.getUserByUserName(email).getEmail());
    if(user==null){
        // send error
        session.setAttribute("message", new Message("user does  not exist :  ","alert-danger"));
        System.out.println("10");
        return  "forgot_email_form";
    }
    else {
        // get

            return "password_change_password";
    }

        } else {
            System.out.println(votp);
            System.out.println("newelse");
            session.setAttribute("message", new Message("Otp invalid :  ","alert-danger"));
            return "verify_otp";


        }




    }


    @PostMapping("/change-password")
    public String change_pass(@RequestParam("newpassword") String newpassword,HttpSession session){
        String email=(String) session.getAttribute("email");
        System.out.println(email);
try{
    System.out.println("change passwod 1");
    User user=this.userRepository.getUserByUserName(email);
    System.out.println(user);
    System.out.println("change passwod 2");
    user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
    System.out.println("change passwod 3");
    this.userRepository.save(user);
    System.out.println("change passwod 4 :  " );
    System.out.println("change passwod 4 :  " );
    return"redirect:/signin?change=password changed sucessfully";
}catch (Exception e){
    e.printStackTrace();
    return "redirect:/change-password";

}
//  return"redirect:/signin?change=password changed sucessfully";

}

}
