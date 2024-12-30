package com.authentication_api.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MessageSource messageSource;

    @GetMapping
    public String admin(Locale locale) {
        return messageSource.getMessage("info.admin.message", null, locale);
    }
}
