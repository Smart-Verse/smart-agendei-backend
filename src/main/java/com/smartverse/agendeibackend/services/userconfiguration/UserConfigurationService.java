package com.smartverse.agendeibackend.services.userconfiguration;

import com.potatotech.authorization.tenant.TenantContext;
import com.smartverse.agendeibackend.config.database.TenantSchemaInterceptor;
import com.smartverse.agendeibackend.config.security.repository.AuthenticationRepository;
import com.smartverse.agendeibackend_gen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserConfigurationService {


    @Autowired
    private TenantSchemaInterceptor tenantSchemaInterceptor;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserConfigurationRepository userConfigurationRepository;

    @Autowired
    private UserConfigurationDTOConverter userConfigurationDTOConverter;


    public UserConfigurationDTO saveUserConfiguration(UUID hash) {
        var oldTEnant = TenantContext.getCurrentTenant();
        TenantContext.setCurrentTenant("admin");
        tenantSchemaInterceptor.switchSchema();

        var user = authenticationRepository.findById(hash);

        var userConfiguration = new UserConfigurationEntity();

        user.ifPresent(item -> {
            userConfiguration.setHash(hash);
            userConfiguration.setName(item.getName());
            userConfiguration.setEmail(item.getEmail());
            userConfiguration.setLang(Language.PORTUGUESE);
            userConfiguration.setTheme(Theme.LIGHT);
            userConfiguration.setPhone(item.getPhone());
        });

        TenantContext.setCurrentTenant(oldTEnant);
        tenantSchemaInterceptor.switchSchema();
        userConfigurationRepository.save(userConfiguration);

        return userConfigurationDTOConverter.toDTO(userConfiguration, null);
    }
    @Transactional
    public void updateMaster(UserConfigurationEntity entity) {
        var oldTEnant = TenantContext.getCurrentTenant();
        TenantContext.setCurrentTenant("admin");
        tenantSchemaInterceptor.switchSchema();

        var user = authenticationRepository.findById(entity.getHash());

        user.ifPresent(item -> {
            item.setName(entity.getName());
            item.setEmail(entity.getEmail());

            authenticationRepository.save(item);
        });

    }
}
