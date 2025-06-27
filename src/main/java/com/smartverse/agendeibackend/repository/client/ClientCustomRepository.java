package com.smartverse.agendeibackend.repository.client;

import com.smartverse.agendeibackend_gen.ClientRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface ClientCustomRepository extends ClientRepository {

    @Query(nativeQuery = true, value = "SELECT count(1) FROM client WHERE status = '0'")
    long countActiveClients();
}
