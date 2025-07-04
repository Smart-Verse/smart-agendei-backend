package com.smartverse.agendeibackend.config.security.model;

import java.util.List;
import java.util.UUID;

public record UsersDTO(
        UUID id,
        String name,
        String email,
        String phone,
        String password,
        String tenant,
        List<String> roles
){}
