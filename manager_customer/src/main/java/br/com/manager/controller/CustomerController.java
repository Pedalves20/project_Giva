package br.com.manager.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.manager.domain.CustomerDto;
import br.com.manager.exception.ResourceNotFoundException;
import br.com.manager.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(tags = "Customer", description = "API Customer")
@RequestMapping(value = "customer")
public class CustomerController {
	
	@Autowired
	private ICustomerService service;
	
	
	@RequestMapping(value = "/hello-world", method = RequestMethod.GET)
	public ResponseEntity<String> getHelloWord() throws ResourceNotFoundException {
		String hello = "";
		try {
			   hello = "Olá pessoal , Sucesso";
		} catch (Exception e) {
			new ResourceNotFoundException("Error search customers");
		}
		return ResponseEntity.ok().body(hello);
	}
	
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDto>> getAll() throws ResourceNotFoundException {
		List<CustomerDto> customer = null;
		try {
			customer = service.listCustomer();
		} catch (Exception e) {
			new ResourceNotFoundException("Error search customers");
		}
		return ResponseEntity.ok().body(customer);
	}

	@RequestMapping(value = "/find/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<CustomerDto> Get(@PathVariable(value = "cpf") int cpf)
			throws ResourceNotFoundException {
		CustomerDto customer = null;
		try {
			customer = service.findByCpf(cpf);
		} catch (Exception e) {
			new ResourceNotFoundException("Error search customer with cpf ::" + cpf);
		}
		if(customer != null) {
			return ResponseEntity.ok().body(customer);
		}else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<CustomerDto> Put(@PathVariable(value = "cpf") int cpf,
			@Valid @RequestBody CustomerDto customerNovo) {
		Optional<CustomerDto> customerAntigo = Optional.ofNullable(service.findByCpf(cpf));
		if (customerAntigo.isPresent()) {
			CustomerDto customerDto = customerAntigo.get();
			customerDto.setCpf(customerNovo.getCpf());
			customerDto.setName(customerNovo.getName());
			customerDto.setAddress(customerNovo.getAddress());
			
			service.updateCustomer(customerDto);
			return new ResponseEntity<CustomerDto>(customerDto, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<CustomerDto> Post(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto customerCriado = new CustomerDto();
		try {
			customerCriado = service.createCustomer(customerDto);
		} catch (Exception e) {
			new ResourceNotFoundException("Not create customer" + customerDto);
		}
		return new ResponseEntity<CustomerDto>(customerCriado, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "cpf") int cpf) {
		Optional<CustomerDto> antigocustomer = Optional.ofNullable(service.findByCpf(cpf));
		if (antigocustomer.isPresent()) {
			service.deleteCustomerByCpf(antigocustomer.get().getCpf());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}