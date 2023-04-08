package com.vietdp.spring.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="itemUOM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtEcomItemUOMEntity {
    @Column(name  = "itemGuid")
    @Id
    private String itemGuid;

    @Column(name  = "UOM")
    private String UOM;

    @Column(name  = "MinPackQty")
    private Float minPackQty;
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
