package co.edu.umanizales.police_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/Prog3")
public class Prog3Controller {
    @GetMapping
    public String gethello()
    {
       return "Hola campeones y campeonas";
    }
}
