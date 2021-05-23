package com.bee.controller;

import com.bee.models.Event;
import com.bee.repository.EventRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String Calendar(Model model) {
        model.addAttribute("message", "Welcome to calendar");
        return "calendar";
    }



    @Autowired
    EventRepository er;

    @RequestMapping("/calendar")
    @ResponseBody
    String home() {
        return "Welcome!";
    }

    @GetMapping("/calendar/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {
        return er.findBetween(start, end);
    }

    @PostMapping("/calendar/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event createEvent(@RequestBody EventCreateParams params) {

        Event e = new Event();
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setText(params.text);

        er.save(e);

        return e;
    }

    @PostMapping("/calendar/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event moveEvent(@RequestBody EventMoveParams params) {

        Event e = er.findById(params.id).get();

        e.setStart(params.start);
        e.setEnd(params.end);

        er.save(e);

        return e;
    }

    @PostMapping("/calendar/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event setColor(@RequestBody SetColorParams params) {

        Event e = er.findById(params.id).get();
        e.setColor(params.color);
        er.save(e);

        return e;
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
        public Long resource;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
        public Long resource;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }
}