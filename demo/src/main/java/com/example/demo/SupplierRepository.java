package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    @Query("SELECT s from Supplier s WHERE CONCAT(s.name, ' ', s.phone, ' ', s.email) LIKE %?1%")
    List<Supplier> search(String keyword);

    @Query("SELECT s from Supplier s WHERE s.supid LIKE ?1")
    List<Supplier> searchById(String keyword);

    @Query("SELECT s from Supplier s WHERE s.name LIKE %?1%")
    List<Supplier> searchByName(String keyword);

    @Query("SELECT s from Supplier s WHERE s.phone LIKE %?1%")
    List<Supplier> searchByPhone(String keyword);

    @Query("SELECT s from Supplier s WHERE s.email LIKE %?1%")
    List<Supplier> searchByEmail(String keyword);

    @Query("SELECT s from Supplier s WHERE s.id != ?1")
    List<Supplier> listAllExceptChosen(Long supid);


}
