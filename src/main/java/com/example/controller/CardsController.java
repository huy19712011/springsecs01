package com.example.controller;

import com.example.model.Cards;
import com.example.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;

    //@GetMapping("/myCards")
    //public String getCardDetails() {
    //
    //    return "Here is the card from the DB";
    //}

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam long id) {

        List<Cards> cards = cardsRepository.findByCustomerId(id);

        if (cards != null) {
            return cards;
        }

        return null;
    }

}
