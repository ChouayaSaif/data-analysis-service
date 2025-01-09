package com.saif.project.storage.repository;

import com.saif.project.storage.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlRepository extends JpaRepository<Address, Integer> {
}
