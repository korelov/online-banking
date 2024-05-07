package org.javaacademy.onlinebanking.entity;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class User {
    @NonNull
    String phone;
    @NonNull
    UUID id;
    @NonNull
    String fullName;
}
