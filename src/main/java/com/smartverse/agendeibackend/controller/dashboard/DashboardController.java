package com.smartverse.agendeibackend.controller.dashboard;

import com.smartverse.agendeibackend.services.dashboard.DashboardService;
import com.smartverse.agendeibackend_gen.Dashboard;
import com.smartverse.agendeibackend_gen.DashboardOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class DashboardController implements Dashboard {

    @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<DashboardOutput> dashboard() {
        DashboardOutput dashboardOutput = dashboardService.getMetrics();
        return ResponseEntity.ok(dashboardOutput);
    }
}
