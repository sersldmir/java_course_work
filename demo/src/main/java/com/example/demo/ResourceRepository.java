package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ResourceRepository extends JpaRepository<Resource, Long>{
    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE CONCAT(r.resid, '', r.name, '', r.type, '', r.quantity, '', r.cost, '', r.acdate, '', s.supid) LIKE %?1%")
    List<Resource> search(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.name LIKE %?1%")
    List<Resource> searchByName(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.type LIKE %?1%")
    List<Resource> searchByType(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.quantity LIKE %?1%")
    List<Resource> searchByQuantity(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.cost LIKE %?1%")
    List<Resource> searchByCost(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE r.acdate LIKE %?1%")
    List<Resource> searchByAcdate(String keyword);

    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE s.supid LIKE %?1%")
    List<Resource> searchBySupplier(String keyword);

}
