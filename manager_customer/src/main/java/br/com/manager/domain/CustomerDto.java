package br.com.manager.domain;

import br.com.manager.model.Address;

public class CustomerDto {

		private Long id;

		private int cpf;
		
		private String name;
		
		private Address address;

		
		
		public int getCpf() {
			return cpf;
		}

		public void setCpf(int cpf) {
			this.cpf = cpf;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		
		

}
