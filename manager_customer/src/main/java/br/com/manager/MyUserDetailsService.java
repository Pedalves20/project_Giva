package br.com.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.manager.domain.UserDto;
import br.com.manager.service.IUserService;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

	

	@Autowired
	private IUserService userService;

	
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("admin", "admin",
                new ArrayList<>());
    }
    
    public UserDetails loadUserByUsernameAndPassword(String userName, String password) throws UsernameNotFoundException {
    	UserDto userdto = new UserDto();
		userdto = userService.findByCpf(userName,password);
        return new User(userdto.getLogin(), userdto.getPassword(),
                new ArrayList<>());
    }
}