package kr.co.sist.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.sist.DTO.ScamDTO;
import kr.co.sist.user.Service.AnticheatService;

@RestController
public class AnticheatController {

    @Autowired
    private AnticheatService anticheatService;

    @GetMapping("/api/anticheat/real-time-monitoring")
    public List<ScamDTO> getRealTimeMonitoring() {
        return anticheatService.selectRealTimeMonitoring();
    }
}
