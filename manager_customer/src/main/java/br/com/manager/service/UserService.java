package br.com.manager.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manager.domain.UserDto;
import br.com.manager.model.User;
import br.com.manager.repository.UserRepository;


@Service
public class UserService implements IUserService {
	
	@Autowired
    private UserRepository customerRepository;
	
	
	@Override
	public UserDto findByCpf(String login, String password) throws ServiceException {
		UserDto userDto = null;
		 User user = customerRepository.findByLoginPassword(login, password);
		//MOCK PARA TESTE
		//User user = new User();
		//user.setLogin("giva");
		//user.setPassword("admin");
		
		if(user != null) {
			userDto = new UserDto();
			userDto.setLogin(user.getLogin());
			userDto.setPassword(user.getPassword());
		}
	 return userDto;
	}

	
	@Override
	public UserDto findByLogin(String login) throws ServiceException {
		UserDto userDto = null;
		 User user = customerRepository.findByLogin(login);
		//MOCK PARA TESTE
		//User user = new User();
		//user.setLogin("giva");
		//user.setPassword("admin");
		
		if(user != null) {
			userDto = new UserDto();
			userDto.setLogin(user.getLogin());
			userDto.setPassword(user.getPassword());
		}
	 return userDto;
	}
		
	}

	