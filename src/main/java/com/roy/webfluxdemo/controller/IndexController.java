package com.roy.webfluxdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class IndexController {

    @ResponseBody
    @GetMapping(value = "/")
    public Flux<Integer> generateToNumbers() {
        return Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).delayElements(Duration.ofMillis(100));
    }
}
