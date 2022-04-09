package com.meli.challmeli.controller;
import com.meli.challmeli.service.InfoIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpController {

    @Autowired
    InfoIpService infoIpService;

    @GetMapping("/registryIp/{ip}")
    public Object registryIp(@PathVariable String ip) {
          return infoIpService.countryInfoComplete(ip);
    }
}
