package br.com.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manager.domain.CustomerDto;
import br.com.manager.model.Address;
import br.com.manager.model.Customer;
import br.com.manager.repository.AddressRepository;
import br.com.manager.repository.CustomerRepository;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository  addressRepository;


	@Override
	public List<CustomerDto> listCustomer() throws ServiceException {
		List<Customer> listCustomer = customerRepository.findAll();
		List<CustomerDto> listCustomerDto = new ArrayList<CustomerDto>();
		CustomerDto customerDto = new CustomerDto();
		for(Customer customer: listCustomer) {
			customerDto.setId(customer.getId());
			customerDto.setCpf(customer.getCpf());
			customerDto.setAddress(customer.getAddress());
			customerDto.setName(customer.getName());
			listCustomerDto.add(customerDto);
		}
		
		return  listCustomerDto;
	} 
	

	@Override
	public CustomerDto findByCpf(int cpfId) throws ServiceException {
		 CustomerDto customerDto = null;
		 Customer customer = customerRepository.findByCpf(cpfId);
		 if(customer != null) {
			 customerDto = new CustomerDto();
			 customerDto.setId(customer.getId());
			 customerDto.setName(customer.getName());
			 customerDto.setCpf(customer.getCpf());
			 customerDto.setAddress(customer.getAddress());
		 }
		return customerDto;
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto) throws ServiceException {
		   addressRepository.updateAddress(customerDto.getAddress().getLogradouro(), customerDto.getAddress().getNumero(),
				   							customerDto.getAddress().getComplemento(), customerDto.getAddress().getBairro(),
				   							customerDto.getAddress().getCidade(),customerDto.getAddress().getEstado(),
				   							customerDto.getAddress().getCep());
		  
		   customerRepository.updateCustomer(customerDto.getName(), customerDto.getCpf(), customerDto.getAddress().getId());
		   
		   
		return customerDto;
	}

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) throws ServiceException {
		
		   Address address = new Address();
		   Customer customer = new Customer();

		   address.setBairro(customerDto.getAddress().getBairro());
		   address.setCep(customerDto.getAddress().getCep());
		   address.setCidade(customerDto.getAddress().getCidade());
		   address.setComplemento(customerDto.getAddress().getComplemento());
		   address.setEstado(customerDto.getAddress().getEstado());
		   address.setLogradouro(customerDto.getAddress().getLogradouro());
		   address.setNumero(customerDto.getAddress().getNumero());

		   address = addressRepository.saveAndFlush(address);

		   customer.setAddress(address);
		   customer.setCpf(customerDto.getCpf());
		   customer.setName(customerDto.getName());
		   
		   customer = customerRepository.save(customer);
		   
		   customerDto.setId(customer.getId());
		   customerDto.setAddress(address);
		   customerDto.setName(customer.getName());
		   customerDto.setCpf(customer.getCpf());
		   
		return customerDto;
	}

	@Override
	public void deleteCustomerByCpf(int cpf) throws ServiceException {
		Customer customer = customerRepository.findByCpf(cpf);
		customerRepository.delete(customer);
	
	}
	
	
	
}
