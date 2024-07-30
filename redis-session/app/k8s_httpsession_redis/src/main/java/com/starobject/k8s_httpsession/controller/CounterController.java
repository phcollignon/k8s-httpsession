package com.starobject.k8s_httpsession.controller;

import com.starobject.k8s_httpsession.model.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class CounterController {

    private static final String COUNTER_SESSION_KEY = "counter";
    private static final Logger logger = LoggerFactory.getLogger(CounterController.class);

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.isNew()) {
            String serverInfo = getServerInfo();
            logger.info("New session created. Server info: {}", serverInfo);
        }
        
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
        String serverInfo = getServerInfo();
        if (counter != null) {
            logger.info("Incrementing counter. Current value: {}. Server info: {}", counter.getCount(), serverInfo);
            counter.increment();
            session.setAttribute(COUNTER_SESSION_KEY, counter);
        } else {
            logger.warn("Counter not found in session. Server info: {}", serverInfo);
        }
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session) {
        Counter counter = (Counter) session.getAttribute(COUNTER_SESSION_KEY);
        String serverInfo = getServerInfo();
        if (counter != null) {
            logger.info("Resetting counter. Server info: {}", serverInfo);
            counter.reset();
            session.setAttribute(COUNTER_SESSION_KEY, counter);
        } else {
            logger.warn("Counter not found in session. Server info: {}", serverInfo);
        }
        return "redirect:/";
    }

    private String getServerInfo() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String hostname = inetAddress.getHostName();
            String ipAddress = inetAddress.getHostAddress();
            return "Hostname: " + hostname + ", IP Address: " + ipAddress;
        } catch (UnknownHostException e) {
            logger.error("Unable to get server information", e);
            return "Unknown host";
        }
    }
}
