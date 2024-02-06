package com.example.demo.user.repository;

import com.example.demo.user.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    @Query(value = "CALL FIND_USER_BY_ID(:inId);", nativeQuery = true)
    List<User> findUserById(@Param("inId") Long id);

    @Procedure(procedureName = "GET_TOTAL_USERS_BY_ID", outputParameterName = "outCount")
    Long getTotalUserCountById(Long id);

    @Query(value = "CALL GET_TOTAL_USERS_BY_ID(:inId, :outCount)", nativeQuery = true)
    Long getTotalUserCountById2(@Param("inId") Long id, @Param("outCount") Long outCount);
}

