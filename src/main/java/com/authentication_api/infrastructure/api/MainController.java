package com.authentication_api.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class  MainController {
    private final MessageSource messageSource;

    @GetMapping("/welcome")
    public String welcome(Locale locale) {
        return messageSource.getMessage("info.welcome", null, locale);
    }
}
