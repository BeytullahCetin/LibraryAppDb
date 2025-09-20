package com.turkcell.LibraryAppDb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.turkcell.LibraryAppDb.entity.Customer;
import com.turkcell.LibraryAppDb.entity.Fine;
import com.turkcell.LibraryAppDb.entity.enums.MemberStatus;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmailIgnoreCase(String email);

	List<Customer> findByMemberStatusAndEmailContainingIgnoreCase(MemberStatus memberStatus, String email);

	@Query("select f from Fine f join f.borrow b where b.customer.id = :customerId and f.isPaid = :isPaid")
	List<Fine> findFinesByCustomerIdAndIsPaid(@Param("customerId") int customerId, @Param("isPaid") boolean isPaid);
}