package be.unamur.hermes.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.parameters.ClaimType;
import be.unamur.hermes.business.service.ClaimService;
import be.unamur.hermes.business.service.InhabitantService;
import be.unamur.hermes.business.service.ParameterService;
import be.unamur.hermes.business.service.PeopleBisService;
import be.unamur.hermes.dataaccess.entity.Claim;
import be.unamur.hermes.dataaccess.entity.PeopleBis;

@RestController
@RequestMapping({ "/" })
public class ApplicationController {

    private InhabitantService inhabitantService;
    private PeopleBisService peopleBisService;
    private ClaimService claimService;
    private ParameterService parameterService;

    @Autowired
    public ApplicationController(InhabitantService inhabitantService, PeopleBisService peopleBisService,
	    ClaimService claimService, ParameterService parameterService) {
	this.inhabitantService = inhabitantService;
	this.peopleBisService = peopleBisService;
	this.claimService = claimService;
	this.parameterService = parameterService;
    }

    @GetMapping
    public String serveHomePage() {
	return "index";
    }

    @GetMapping(path = "/about")
    public String serveAboutPage() {
	System.out.println("About bitches");
	inhabitantService.register("Marco", "Polo");
	return "about";
    }

    @PostMapping(path = "/createpeople")
    public ResponseEntity<Void> createPeople(@RequestBody PeopleBis peopleBis) {
	inhabitantService.register(peopleBis.getFirstName(), peopleBis.getLastName());
	return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/showpeople")
    public ResponseEntity<List<PeopleBis>> showPeople() {
	return ResponseEntity.status(HttpStatus.OK).body(peopleBisService.findAll());
    }

    @GetMapping(path = "/claims/{claimId}")
    public ResponseEntity<Claim> showClaim(@PathVariable(value = "claimId") long claimId) {
	return ResponseEntity.status(HttpStatus.OK).body(claimService.find(claimId));
    }

    @GetMapping(path = "/parameters/{municipality}")
    public ResponseEntity<ClaimType> getClaimType(@PathVariable(value = "municipality") String municipality,
	    @RequestParam("claimTypeId") String claimTypeId) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getClaimType(municipality, claimTypeId));
    }

    @GetMapping(path = "/parameters/{municipality}/all")
    public ResponseEntity<List<ClaimType>> getClaimTypes(@PathVariable(value = "municipality") String municipality) {
	return ResponseEntity.status(HttpStatus.OK).body(parameterService.getClaimTypes(municipality));
    }

    @GetMapping(path = "/parameters/{municipality}/{claimTypeId}")
    public ResponseEntity<String> getParameter(@PathVariable(value = "municipality") String municipality,
	    @PathVariable(value = "claimTypeId") String claimTypeId, @RequestParam("parameterId") String parameterId) {
	// e.g. to know if a type of claim is activated :
	// "/parameters/Gembloux/perm-parking?parameterId=activated"
	return ResponseEntity.status(HttpStatus.OK)
		.body(parameterService.getParameter(municipality, claimTypeId, parameterId));
    }
}
