package com.ssafy.a105.db.repository;

import com.ssafy.a105.db.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
