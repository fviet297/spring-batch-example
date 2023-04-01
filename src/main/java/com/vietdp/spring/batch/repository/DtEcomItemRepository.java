package com.vietdp.spring.batch.repository;

import com.vietdp.spring.batch.entity.DtEcomItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface DtEcomItemRepository extends JpaRepository<DtEcomItemEntity,String> {
}
