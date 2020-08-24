package br.com.manager.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.manager.model.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	

	@Query("SELECT c FROM Customer c WHERE c.cpf = ?1")
	Customer findByCpf(int id);
	
	
	@Modifying
	@Query("delete from Customer c where c.cpf=:cpf")
	void deleteCustomerByCpf(@Param("cpf") long  cpf);
	
	
	@Modifying
    @Query("update Customer c set c.address.id = :id WHERE c.cpf = :cpf")
	@Transactional
    void updateCustomerAddress(@Param("cpf") long cpf, @Param("id") long id);
	 
	
	@Modifying
	@Query("update Customer c set c.name= :name ,  c.address.id= :addressId  WHERE cpf = :cpf")
	@Transactional
	void updateCustomer(@Param("name")String name ,@Param("cpf") int cpf, @Param("addressId")long addressId);
	
	
	@Modifying
	@Query(value = "INSERT INTO Customer(cpf, name, address ) "
			+ " VALUES (:cpf,:name,:addressId)", nativeQuery = true)
	@Transactional
	Integer createCustomer(@Param("cpf") long cpf, 
			   @Param("name") String name, @Param("addressId") long addressId);

		
	}
