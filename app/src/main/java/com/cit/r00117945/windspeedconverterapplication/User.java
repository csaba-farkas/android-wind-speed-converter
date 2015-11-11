/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */
package com.cit.r00117945.windspeedconverterapplication;

/**
 * A simple user object which is created when user is "registering".
 */
public class User {
    private String name;
    private String email;

    /**
     * Constructor method creates a new instance of this class.
     *
     * @param name  indicates user's name.
     * @param email indicates user's email address.
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Getter method for user's name.
     * @return user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for user's email address.
     * @return user's email address.
     */
    public String getEmail() {
        return email;
    }
}
