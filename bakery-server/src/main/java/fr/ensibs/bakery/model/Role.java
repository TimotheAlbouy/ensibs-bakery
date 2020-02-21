package fr.ensibs.bakery.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Role enumeration for users in the database.
 */
@XmlRootElement(name = "Role")
public enum Role {
    ADMIN,
    CUSTOMER
}
