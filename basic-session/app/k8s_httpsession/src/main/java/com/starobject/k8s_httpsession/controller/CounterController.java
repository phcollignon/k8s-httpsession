// src/main/java/com/starobject/k8_httpsession/controller/CounterController.java
package com.starobject.k8s_httpsession.controller;

import com.starobject.k8s_httpsession.model.Counter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class CounterController {

    private static final String COUNTER_SESSION_KEY = "counter";

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        Counter counter = (Counter) session.getAttribute(COUNTER_SESSION_KEY);
        if (counter == null) {
            counter = new Counter();
            session.setAttribute(COUNTER_SESSION_KEY, counter);
        }
        model.addAttribute("counter", counter);
        return "index";
    }

    @PostMapping("/increment")
    public String increment(HttpSession session) {
        Counter counter = (Counter) session.getAttribute(COUNTER_SESSION_KEY);
        if (counter != null) {
            counter.increment();
        }
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session) {
        Counter counter = (Counter) session.getAttribute(COUNTER_SESSION_KEY);
        if (counter != null) {
            counter.reset();
        }
        return "redirect:/";
    }
}
