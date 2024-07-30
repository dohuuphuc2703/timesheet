package com.example.timesheet.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.projects p WHERE p.id = :projectId")
    Set<User> findUsersByProjectId(@Param("projectId") int projectId);

    @Query("SELECT u FROM User u JOIN u.timeSheet t WHERE t.id = :timeSheetId")
    User findUsersByTimeSheetId(@Param("timeSheetId") int timeSheetId);

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findUsersByRoleName(String roleName);

    @Query(
            "SELECT u FROM User u WHERE (:name IS NULL OR u.name LIKE %:name%) AND (:branch IS NULL OR u.branch.name = :branch) AND (:position IS NULL OR u.position.name = :position) AND (:type IS NULL OR u.type = :type) AND (:level IS NULL OR u.level = :level) AND (:isActive IS NULL OR u.isActive = :isActive)")
    Page<User> findByCriteria(
            @Param("name") String name,
            @Param("branch") String branch,
            @Param("position") String position,
            @Param("type") String type,
            @Param("level") String level,
            @Param("isActive") Boolean isActive,
            Pageable pageable);
}
