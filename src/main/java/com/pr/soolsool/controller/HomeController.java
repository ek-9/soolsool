package com.pr.soolsool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        log.info("login home");

        // 인증된 사용자 정보에서 username 가져오기
        if (authentication.getPrincipal() != "anonymousUser") {
            model.addAttribute("user", authentication);
            log.info(name);
        }
        else {
            log.info("no user");
        }
        return "home1";  // 홈 페이지로 이동
    }
}
