package com.vietdp.spring.batch.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "dtEcomBranch")
public class DtEcomBranch {

    @JacksonXmlProperty(localName = "BranchID")
    private String branchId;

    @JacksonXmlProperty(localName = "dtEcomItem")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DtEcomItem> dtEcomItem;
}