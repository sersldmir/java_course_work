package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ResourceRepository extends JpaRepository<Resource, Long>{
    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE CONCAT(r.name, '', r.type, '', r.quantity, '', r.cost, '', r.acdate, '', s.name) LIKE %?1%")
    List<Resource> search(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.name LIKE %?1%")
    List<Resource> searchByName(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.type LIKE %?1%")
    List<Resource> searchByType(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.quantity LIKE ?1")
    List<Resource> searchByQuantity(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.cost LIKE ?1")
    List<Resource> searchByCost(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.acdate LIKE %?1%")
    List<Resource> searchByAcdate(String keyword);

    @Query("SELECT r FROM Supplier s, Resource r WHERE r.supplier = s.supid AND s.name LIKE %?1%")
    List<Resource> searchBySupplier(String keyword);

    @Query("SELECT s.name FROM Supplier s, Resource r WHERE r.supplier = s.supid AND s.name LIKE %?1%")
    List<String> searchSupplierNames(String keyword);

    @Query("SELECT s.name FROM Supplier s, Resource r WHERE r.supplier = s.supid ORDER BY r.resid")
    List<String> extractSupplier();



}
