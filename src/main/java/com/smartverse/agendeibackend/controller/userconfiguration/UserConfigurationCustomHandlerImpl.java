package com.smartverse.agendeibackend.controller.userconfiguration;


import com.smartverse.agendeibackend.repository.userconfiguration.UserConfigurationCustomRepository;
import com.smartverse.agendeibackend.services.userconfiguration.UserConfigurationService;
import com.smartverse.agendeibackend_gen.GetUser;
import com.smartverse.agendeibackend_gen.GetUserOutput;
import com.smartverse.agendeibackend_gen.UserConfigurationDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(origins="*")
public class UserConfigurationCustomHandlerImpl implements GetUser {

    @Autowired
    UserConfigurationCustomRepository userConfigurationCustomRepository;

    @Autowired
    UserConfigurationDTOConverter userConfigurationDTOConverter;

    @Autowired
    UserConfigurationService userConfigurationService;

    @Override
    public ResponseEntity<GetUserOutput> getUser(UUID hash) {

        var output = new GetUserOutput();
        var user = userConfigurationCustomRepository.findByHash(hash);

        if(user.isEmpty()) {
            output.output = userConfigurationService.saveUserConfiguration(hash);
        } else {
            user.ifPresent(item -> {
                output.output = userConfigurationDTOConverter.toDTO(item, null);
            });
        }
        return ResponseEntity.ok(output);
    }
}
