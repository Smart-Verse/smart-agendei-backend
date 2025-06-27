package com.smartverse.agendeibackend.services.dashboard;

import com.smartverse.agendeibackend.repository.AppointmentCustomRepository;
import com.smartverse.agendeibackend.repository.client.ClientCustomRepository;
import com.smartverse.agendeibackend_gen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ClientCustomRepository clientRepository;

    @Autowired
    private AppointmentCustomRepository appointmentRepository;

    @Autowired
    AppointmentDTOConverter appointmentDTOConverter;

    @Autowired
    private ClientDTOConverter clientDTOConverter;


    public DashboardOutput getMetrics() {
        var dashboardOutput = new DashboardOutput();

        var mapClients = new ArrayList<Map<String, Object>>();
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN); // 00:00:00
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        var appointments = appointmentRepository.findByStartDateBetween(startOfDay, endOfDay);
        var clients = appointmentRepository.findTop5ClientsByAppointmentCount();

        clients.forEach(client -> {
            var map = new LinkedHashMap<String, Object>();
            map.put("client", clientDTOConverter.toDTO((ClientEntity) client[0], null));
            map.put("count", client[1]);
            mapClients.add(map);
        });

        dashboardOutput.countAppointmentCompletedWeek = appointmentRepository.countByStatusAndLast7Days(LocalDateTime.now().minusDays(7).with(LocalTime.MIN));
        dashboardOutput.countAppointmentPendingConfirmation = appointmentRepository.countAppointmentsPending();
        dashboardOutput.countClientsActive = clientRepository.countActiveClients();
        dashboardOutput.appointemntsToday = appointmentDTOConverter.toDTO(appointments, null);
        dashboardOutput.countAppointmentToday = (long) appointments.size();
        dashboardOutput.topClients = mapClients;

        return dashboardOutput;
    }
}
