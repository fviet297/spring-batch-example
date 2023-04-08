package com.vietdp.spring.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "dtEcomItemUOM")
@Data
public class DtEcomItemUOM {

    @JacksonXmlProperty(localName = "ItemGUID")
    private String itemGuid;

    @JacksonXmlProperty(localName = "UOM")
    private String UOM;

    @JacksonXmlProperty(localName = "MinPackQty")
    private Float minPackQty;

}
