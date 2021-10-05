package com.project.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.test.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{

}
