package com.example.demo.role.repository;

import com.example.demo.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "TRUNCATE role", nativeQuery = true)
    void truncate();
}
