package fr.ensibs.bakery.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A user of the bakery.
 */
@XmlRootElement(name = "User")
public class User {

    /**
     * the id of the user
     */
    private int id;

    /**
     * the name of the user
     */
    private String name;

    /**
     * the password hash of the user
     */
    private String passwordHash;

    /**
     * the role of the user (customer/admin)
     */
    private Role role;

    /**
     * the token of the user
     */
    private String token;

    /**
     * No-argument constructor.
     */
    User() { }

    /**
     * Package-only constructor.
     * @param id the id of the user
     * @param name the name of the user
     * @param passwordHash the password hash of the user
     * @param role the role of the user (customer/admin)
     * @param token the token of the user
     */
    User(int id, String name, String passwordHash, Role role, String token) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
        this.token = token;
    }

    /**
     * Get the id of the user.
     * @return the id of the user
     */
    @XmlElement(name = "getId")
    public int getId() {
        return id;
    }

    /**
     * Get the name of the user.
     * @return the name of the user
     */
    @XmlElement(name = "getName")
    public String getName() {
        return name;
    }

    /**
     * Get the password hash of the user.
     * @return the password hash of the user
     */
    @XmlElement(name = "getPasswordHash")
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Get the role of the user.
     * @return the role of the user
     */
    @XmlElement(name = "getRole")
    public Role getRole() {
        return role;
    }

    /**
     * Get the token of the user.
     * @return the token of the user
     */
    @XmlElement(name = "getToken")
    public String getToken() {
        return token;
    }

}
