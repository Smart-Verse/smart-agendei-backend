package com.smartverse.agendeibackend.repository.userconfirmation;


import com.smartverse.agendeibackend_gen.UserConfirmationEntity;
import com.smartverse.agendeibackend_gen.UserConfirmationRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfirmationCustomRepository extends UserConfirmationRepository {
    Optional<UserConfirmationEntity> findByHash(String hash);
}
