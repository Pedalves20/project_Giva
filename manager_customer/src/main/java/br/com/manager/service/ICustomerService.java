package br.com.manager.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import br.com.manager.domain.CustomerDto;


public interface ICustomerService {

		   List<CustomerDto> listCustomer() throws ServiceException;
		   
		   CustomerDto findByCpf(int cpf) throws ServiceException;
		   
		   CustomerDto updateCustomer(CustomerDto customerDto) throws ServiceException;
		   
		   CustomerDto createCustomer(CustomerDto customerDto) throws ServiceException;

		   void deleteCustomerByCpf(int cpf) throws ServiceException;

		  
		  }
