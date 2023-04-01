package com.vietdp.spring.batch.repository;

import com.vietdp.spring.batch.entity.DtEcomItemUOMEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface ItemUOMERepository extends JpaRepository<DtEcomItemUOMEntity,String> {
}
