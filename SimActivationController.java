package com.example.telstra.controller;

import com.example.telstra.model.SimCardActivation;
import com.example.telstra.service.SimActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/sim")
public class SimActivationController {

    private final SimActivationService activationService;

    public SimActivationController(SimActivationService activationService) {
        this.activationService = activationService;
    }

    @PostMapping("/activate")
    public ResponseEntity<SimCardActivation> activateSim(@RequestBody Map<String, String> requestBody) {
        if (!requestBody.containsKey("iccid") || !requestBody.containsKey("customerEmail")) {
            return ResponseEntity.badRequest().build();
        }

        SimCardActivation activation = activationService.activateSim(
                requestBody.get("iccid"),
                requestBody.get("customerEmail")
        );

        return ResponseEntity.ok(activation);
    }

    @GetMapping("/query")
    public ResponseEntity<SimCardActivation> getSimById(@RequestParam Long simCardId) {
        Optional<SimCardActivation> sim = activationService.getSimById(simCardId);
        return sim.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
