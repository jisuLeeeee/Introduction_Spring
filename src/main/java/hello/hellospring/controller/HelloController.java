package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // controller에서는 해당 어노테이션을 달아주어야함
public class HelloController {
    @GetMapping("hello") // localhost:8080/hello가 입력되면 아래 메소드 호출
    public String hello(Model model){
        model.addAttribute("data", "hello!!"); // model에 key-value를 담아 view로 넘김
        return "hello"; // resources/templates/hello.html로 이동
    }
}
