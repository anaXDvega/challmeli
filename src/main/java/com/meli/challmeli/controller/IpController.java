package com.meli.challmeli.controller;

import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.service.InfoIpService;
import com.meli.challmeli.service.distance.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IpController {

    @Autowired
    InfoIpService infoIpService;

    @Autowired
    DistanceService distanceService;

    @GetMapping("/main")
    public String greeting() {
        return "main.js";
    }

    @GetMapping("/registryIp/{ip}")
    public Object registryIp(@PathVariable String ip) {
        return infoIpService.countryInfoComplete(ip);
    }

    @GetMapping("/findAll")
    public Iterable<Distance> findAll() {
        return distanceService.findAllDistance();
    }


}
