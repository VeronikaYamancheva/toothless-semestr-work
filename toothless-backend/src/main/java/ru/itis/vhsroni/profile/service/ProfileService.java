package ru.itis.vhsroni.profile.service;

import java.util.UUID;

public interface ProfileService<T> {

    T getProfile(UUID userData);
}
