package com.task3.task3.controllers;


import com.task3.task3.models.Posting;
import com.task3.task3.repo.PostingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;


@Controller
public class MainController {

    @Autowired
    private PostingRepository postingRepository;

    @GetMapping("/")
    public String getPostings(@RequestParam(required = false) String period, @RequestParam(required = false) boolean authorized, Model model) {
        // обрабатываем запрос и возвращаем данные из базы
        Iterable<Posting> postings;
        if (period != null) {
            // обрабатываем запрос с фильтром по периоду

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate;


            switch (period) {
                case "day":
                    endDate = endDate.minusDays(1);
                    break;
                case "month":
                    endDate = endDate.minusMonths(1);
                    break;
                case "quarter":
                    endDate = endDate.minusMonths(3);
                    break;
                case "year":
                    endDate = endDate.minusYears(1);
                    break;
                default:
                    // обработка ошибки или ничего не делать
            }


            if (authorized) {
                postings = postingRepository.findByPstngdateBetweenAndAuthdelivery( endDate, startDate,  true);
            } else {
                postings = postingRepository.findByPstngdateBetween( endDate,  startDate);
            }
        } else {
            if (authorized) {
                postings = postingRepository.findByAuthdelivery(true);
            } else {
                postings = postingRepository.findAll();
            }
        }
        model.addAttribute("postings", postings);//данные передаваемые в шаблон
        return "home";//вызов html шаблона
    }
}
