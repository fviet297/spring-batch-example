package com.vietdp.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="DtEcomItem")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtEcomItemEntity {

    @Id
    @Column(name  = "itemGuid")
    private String itemGUID;

    @Column(name  = "ItemCode")
    private String itemCode;
    @Column(name  = "branchId")
    private String branchId;

    @Column(name  = "ExtendedDescription")
    private String extendedDescription;

    @Column(name  = "OnOrderQty")
    private Float onOrderQty;

    @Column(name  = "OnHandQty")
    private Float onHandQty;

    @Column(name  = "ItemDescription")
    private String itemDescription;

    @Column(name  = "MinPackQty")
    private Float minPackQty;

    @Column(name  = "ItemType")
    private String itemType;

    @Column(name  = "ProductGroupMinorGUID")
    private String productGroupMinorGUID;

    @Column(name  = "ProductGroupMajorGUID")
    private String productGroupMajorGUID;

    @Column(name  = "UOM")
    private String uom;

    @Column(name  = "ItemSize")
    private String itemSize;

    @Column(name  = "Configurable")
    private Boolean configurable;

    @Column(name  = "KeywordString")
    private String keywordString;

    @Column(name  = "DisplayComplementaryItems")
    private Boolean displayComplementaryItems;

    @Column(name  = "Stock")
    private Boolean stock;

    @Column(name  = "Discontinued")
    private String discontinued;

    @Column(name  = "AllowRMAinPV")
    private Boolean allowRMAinPV;

    @Column(name  = "StockingUOM")
    private String stockingUOM;

    @Column(name  = "AvailableQty")
    private Float availableQty;

    @Column(name  = "MinPackUOM")
    private String minPackUOM;

    @Column(name  = "ECommerceDescription")
    private String eCommerceDescription;
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
