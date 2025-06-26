package com.smartverse.agendeibackend_gen;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.UUID;


public interface GetUser {

    
    @GetMapping("getUser")
    public ResponseEntity<GetUserOutput> getUser(
        @RequestParam(value="hash",required=false) UUID hash
    );
}
