package com.example.groupproject.p2pFunction;

/**
 * Using this interface to let other class extent for,
 * then they can use onUserClicked methods
 *
 * @author ${Wendi Fan}
 * @studentId ${u7041989}
 */

public interface UserListener {
    void onUserClicked(User user);
}
