package com.vietdp.spring.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@Data
@XmlRootElement(name = "dtEcomItem")
public class DtEcomItem {

    @JacksonXmlProperty(localName = "ItemGUID")
    private String itemGUID;

    @JacksonXmlProperty(localName = "ItemCode")
    private String itemCode;
    private String branchId;
    @JacksonXmlProperty(localName = "ExtendedDescription")
    private String extendedDescription;

    @JacksonXmlProperty(localName = "OnOrderQty")
    private Float onOrderQty;

    @JacksonXmlProperty(localName = "OnHandQty")
    private Float onHandQty;

    @JacksonXmlProperty(localName = "ItemDescription")
    private String itemDescription;

    @JacksonXmlProperty(localName = "MinPackQty")
    private Float minPackQty;

    @JacksonXmlProperty(localName = "dtEcomItemUOM")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DtEcomItemUOM> dtEcomItemUOMs;

    @JacksonXmlProperty(localName = "ItemType")
    private String itemType;

    @JacksonXmlProperty(localName = "ProductGroupMinorGUID")
    private String productGroupMinorGUID;

    @JacksonXmlProperty(localName = "ProductGroupMajorGUID")
    private String productGroupMajorGUID;

    @JacksonXmlProperty(localName = "UOM")
    private String uom;

    @JacksonXmlProperty(localName = "ItemSize")
    private String itemSize;

    @JacksonXmlProperty(localName = "Configurable")
    private Boolean configurable;

    @JacksonXmlProperty(localName = "KeywordString")
    private String keywordString;

    @JacksonXmlProperty(localName = "DisplayComplementaryItems")
    private Boolean displayComplementaryItems;

    @JacksonXmlProperty(localName = "Stock")
    private Boolean stock;

    @JacksonXmlProperty(localName = "Discontinued")
    private String discontinued;

    @JacksonXmlProperty(localName = "AllowRMAinPV")
    private Boolean allowRMAinPV;

    @JacksonXmlProperty(localName = "StockingUOM")
    private String stockingUOM;

    @JacksonXmlProperty(localName = "AvailableQty")
    private Float availableQty;

    @JacksonXmlProperty(localName = "MinPackUOM")
    private String minPackUOM;

    @JacksonXmlProperty(localName = "ECommerceDescription")
    private String eCommerceDescription;
}