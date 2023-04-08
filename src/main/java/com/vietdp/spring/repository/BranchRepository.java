package com.vietdp.spring.repository;

import com.vietdp.spring.entity.DtEcomBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<DtEcomBranchEntity,String> {
}
