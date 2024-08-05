package com.SmartContactManager.SmartContactManagerSpring.Myconf;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.SmartContactManager.SmartContactManagerSpring.Entites.User;
import com.SmartContactManager.SmartContactManagerSpring.dao.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{
   
	
	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.getUserByUserName(username);
		
		
		
		
		
		if(user==null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
//		fetching user from data base
	}

}
