package hr.algebra.java.web.lego.legostorewithdata.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/legoStore/login.html").setViewName("login");
        registry.addViewController("/legoStore/homePage.html").setViewName("homePage");
        registry.addViewController("/legoStore/saveNewLegoPiece.html").setViewName("savePiece");
    }
}
