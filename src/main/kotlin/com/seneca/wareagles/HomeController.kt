package com.seneca.wareagles

import io.jstach.opt.spring.webmvc.JStachioModelView
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.View

@Controller
@RequestMapping("/")
class HomeController {
    @GetMapping("/")
    fun home(): View {
        return JStachioModelView.of(DemoModel("World", 100))
    }
}
