package br.com.manager.service;

import org.hibernate.service.spi.ServiceException;

import br.com.manager.domain.UserDto;

public interface IUserService {
	
		   UserDto findByCpf(String login, String password) throws ServiceException;
		  
		 }
