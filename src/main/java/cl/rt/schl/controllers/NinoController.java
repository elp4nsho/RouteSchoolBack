package cl.rt.schl.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.rt.schl.services.LinkServiceImpl;
@RestController
@RequestMapping("link")
public class NinoController {

	@Autowired private LinkServiceImpl linkService;

	

}