package com.smartverse.agendeibackend.config.security.service;

import com.potatotech.authorization.exception.ServiceException;
import com.potatotech.authorization.security.Authenticate;
import com.potatotech.authorization.security.UserSupplier;
import com.potatotech.authorization.tenant.TenantContext;

import com.smartverse.agendeibackend.config.context.ConfigContextImpl;
import com.smartverse.agendeibackend.config.security.model.RegisterDTO;
import com.smartverse.agendeibackend.config.security.model.UsersDTO;
import com.smartverse.agendeibackend.config.security.model.UsersEntity;
import com.smartverse.agendeibackend.config.security.repository.AuthenticationRepository;
import com.smartverse.agendeibackend.services.email.EmailService;
import com.smartverse.agendeibackend_gen.UserConfirmationEntity;
import com.smartverse.agendeibackend_gen.UserConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    Authenticate authenticate;

    @Autowired
    EmailService emailService;

    @Autowired
    UserConfirmationRepository userConfirmationRepository;


    public String login(UsersDTO userSupplierDTO){
        TenantContext.setCurrentTenant("admin");
        var userSupplierEntity = authenticationRepository.findOneByEmail(userSupplierDTO.email());
        if(userSupplierEntity.isPresent() && new BCryptPasswordEncoder().matches(userSupplierDTO.password(),userSupplierEntity.get().getPassword())){
            return authenticate.generateToken(setUserSupplier(userSupplierEntity.get()));
        } else {
            throw new ServiceException(HttpStatus.UNAUTHORIZED,"User or password invalid");
        }
    }

    public UserSupplier validateToken(String token){
        token = token.replace("Bearer ","");
        return authenticate.isAuthenticated(token);
    }

    private UserSupplier setUserSupplier(UsersEntity userSupplier){
        var usersup =  UserSupplier.builder().build();
        usersup.setId(userSupplier.getId());
        usersup.setName(userSupplier.getName());
        usersup.setTenant(userSupplier.getTenant());
        usersup.setEmail(userSupplier.getEmail());
        usersup.setGroupRoles(Collections.emptyList());
        return usersup;
    }

    @Transactional
    public boolean onRegisterUser(RegisterDTO register){

        if(register.name().isEmpty() || register.password().isEmpty() || register.email().isEmpty()){
            throw new ServiceException(HttpStatus.BAD_REQUEST,"Campos com dados inválidos");
        }

        var email = authenticationRepository.existsByEmail(register.email());

        if(email){
            throw new ServiceException(HttpStatus.FORBIDDEN,"Ja existe um email cadastrado!");
        }

        var count = authenticationRepository.countAllBy();
        var user = new UsersEntity();
        user.setName(register.name());
        user.setEmail(register.email());
        user.setPhone(register.phone());
        var pass = new BCryptPasswordEncoder().encode(register.password());
        user.setPassword(pass);
        user.setUserConfirm(false);
        user.setActive(false);
        user.setTenant(String.format("SMARTVERSE_%s",count));

        user = authenticationRepository.save(user);

        var userConfirmation = new UserConfirmationEntity();

        userConfirmation.setUserId(user.getId());
        userConfirmation.setHash(UUID.randomUUID().toString());
        userConfirmation = userConfirmationRepository.save(userConfirmation);


        return true;
    }
}
