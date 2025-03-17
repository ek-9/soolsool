package com.pr.soolsool.controller;

import com.pr.soolsool.domain.Users;
import com.pr.soolsool.dto.UserDTO;
import com.pr.soolsool.service.BoardService;
import com.pr.soolsool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/join")
    public String joinForm(UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        log.info("joinForm");
        return "pages/join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("userDTO") UserDTO userDTO) {
        log.info("postmapping join");;
        log.info("service save start");
        userService.save(userDTO);
        log.info("service save finish");
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        log.info("Getmapping LoginForm");
        return "pages/loginForm";
    }

    @PostMapping("/loginForm")
    public String login() {
        log.info("Postmapping login");
        return "redirect:/";
    }

    @GetMapping("/myTravel")
    public String goMyPage(Model model) {
        Users findUser = boardService.checkUser();
        log.info("go my page, finduser name = {}", findUser.getUsername());
        model.addAttribute("user",findUser);
        return "/pages/myTravel";
    }

}
