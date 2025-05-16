package co.tz.metro.controllers;

import co.tz.metro.fusion.entity.LoanSchedule;
import co.tz.metro.services.LoanScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-schedules")
public class LoanScheduleController {

    @Autowired
    private LoanScheduleService loanScheduleService;

    @PostMapping("/loan/{loanId}")
    public LoanSchedule createLoanSchedule(@PathVariable Long loanId, @RequestBody LoanSchedule loanSchedule) {
        return loanScheduleService.createLoanSchedule(loanId, loanSchedule);
    }

    @GetMapping("/loan/{loanId}")
    public List<LoanSchedule> getLoanSchedulesByLoanId(@PathVariable Long loanId) {
        return loanScheduleService.getLoanSchedulesByLoanId(loanId);
    }
}
