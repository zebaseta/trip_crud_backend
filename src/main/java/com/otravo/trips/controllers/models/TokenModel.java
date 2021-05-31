package com.otravo.trips.controllers.models;

import com.sun.xml.txw2.annotation.XmlNamespace;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenModel {
    private String authorization;
}
