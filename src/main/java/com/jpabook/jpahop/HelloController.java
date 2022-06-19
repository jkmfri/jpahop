package com.jpabook.jpahop;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        System.out.println("test");
        model.addAttribute("data", "hello!!!");
        return "hello"; // resources/templates 아래에 생성된 html
    }
}
