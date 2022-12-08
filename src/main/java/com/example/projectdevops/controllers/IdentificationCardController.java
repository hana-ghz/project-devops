package com.example.projectdevops.controllers;

import com.example.projectdevops.models.IdentificationCard;
import com.example.projectdevops.services.IdentificationCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/identification")
@CrossOrigin(origins = "*")
public class IdentificationCardController {
    @Autowired
    IdentificationCardService idCardService;

    @GetMapping("/all")
    public List<IdentificationCard> getIdCards()
    {
        List<IdentificationCard> idCards = this.idCardService.getAllIds();
        return idCards;
    }

    @GetMapping("/{cardId}")
    public IdentificationCard getIdentificationCardById(@PathVariable("cardId") Long cardId)
    {
        IdentificationCard idCard = this.idCardService.getIdCardById(cardId);
        return idCard;
    }

    @PostMapping("/create/{studentId}")
    public IdentificationCard createNewCard(@RequestBody IdentificationCard idCard, @PathVariable("studentId") Long studentId)
    {
        IdentificationCard card = this.idCardService.createIdentificationCard(idCard, studentId);
        return card;
    }
}
