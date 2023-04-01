package com.vietdp.spring.batch.repository;

import com.vietdp.spring.batch.entity.DtEcomBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<DtEcomBranchEntity,String> {
}
