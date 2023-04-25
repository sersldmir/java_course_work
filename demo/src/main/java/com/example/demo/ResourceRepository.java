package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ResourceRepository extends JpaRepository<Resource, Long>{
    @Query("SELECT r FROM Resource r JOIN Supplier s WHERE CONCAT(r.resid, '', r.resname, '', r.type, '', r.quantity, '', r.cost, '', r.acdate, '', s.supid) LIKE %?1%")
    List<Resource> search(String keyword);
}
