package com.example.groupproject.p2pFunction;

import java.io.Serializable;

/**
 * This Class is the instance of our User,
 * it contains all the attributes we need,
 * all the attributes also match the names in firebase database
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public class User implements Serializable {
    public String name, image, email, token, id;
}
