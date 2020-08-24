/*
 * www.paulocollares.com.br
 */
package br.com.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Main controller responsable to redirect documentation
 * swagger
 *
 * @author Givanildo
 */
@Controller
@RequestMapping("/customer/")
public class MainController {

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        return new RedirectView("swagger-ui.html");
    }
}
