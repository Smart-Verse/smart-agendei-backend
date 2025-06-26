package com.smartverse.agendeibackend.repository.userconfiguration;


import com.smartverse.agendeibackend_gen.UserConfigurationEntity;
import com.smartverse.agendeibackend_gen.UserConfigurationRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public interface UserConfigurationCustomRepository extends UserConfigurationRepository {

    Optional<UserConfigurationEntity> findByHash(UUID hash);
}
