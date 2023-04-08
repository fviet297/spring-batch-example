package com.vietdp.spring.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "dsEcomItem")
public class DsEcomItem implements Serializable {
    @JacksonXmlProperty(localName = "dtEcomBranch")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DtEcomBranch dtEcomBranch;

}
