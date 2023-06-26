package hr.algebra.java.web.lego.legostorewithdata.controller;

import hr.algebra.java.web.lego.legostorewithdata.domain.Log;
import hr.algebra.java.web.lego.legostorewithdata.repository.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/logs")
public class LogController {

    private LogRepository logRepository;


    @GetMapping("/logDetails.html")
    public String viewLogs(Model model) {
        List<Log> logs = logRepository.getAllLogs();
        model.addAttribute("logs", logs);
        return "logDetails.html";
    }
}
