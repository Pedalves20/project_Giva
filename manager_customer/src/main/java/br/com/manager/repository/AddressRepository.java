package br.com.manager.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.manager.model.Address;



@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
		
		@Query("SELECT EN FROM Address EN where EN.logradouro = ?1 and EN.numero = ?2 and EN.cep = ?3 ")
		Address findEnderecoByLogradouroNumeroCep(String logradouro, String numero, String cep);
		
		
		@Modifying
		@Query(value = "INSERT INTO Address(cpf, name, address ) "
				+ " VALUES (:cpf,:name,:addressId)", nativeQuery = true)
		@Transactional
		Integer createCustomer(@Param("cpf") long cpf, 
				   @Param("name") String name, @Param("addressId") long addressId);

		@Modifying
		@Query("update Address set logradouro= :logradouro ,  numero= :numero, complemento= :complemento, "
				+ "                 bairro= :bairro, cidade= :cidade, estado= :estado WHERE cep = :cep")
		@Transactional
		void updateAddress(@Param("logradouro")String logradouro ,@Param("numero") String numero, 
				                  @Param("complemento")String complemento,
				                  @Param("bairro")String bairro, @Param("cidade")String cidade,
				                  @Param("estado")String estado,@Param("cep")String cep);
		
		
	}
