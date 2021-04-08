package com.tannerlee.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("tannerlee")
public class EchoController {

    @RequestMapping(value = "echo", method = {RequestMethod.GET})
    public String base(HttpServletRequest request, String s) {
        return s;
    }
}
