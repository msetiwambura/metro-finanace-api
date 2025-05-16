package co.tz.metro.controllers;

import co.tz.metro.data.ApiResponse;
import co.tz.metro.data.IdResponse;
import co.tz.metro.dto.RepaymentDtos.RepaymentDTO;
import co.tz.metro.fusion.entity.Repayment;
import co.tz.metro.services.RepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repayments")
public class RepaymentController {

    @Autowired
    private RepaymentService repaymentService;

    @PostMapping("/loan/{loanId}")
    public ResponseEntity<ApiResponse<Repayment>> makeRepayment(@RequestBody RepaymentDTO repaymentDTO, @PathVariable Long loanId) {
        Repayment repayment = repaymentService.makeRepayment(repaymentDTO, loanId);
        ApiResponse<Repayment> res = new ApiResponse<>(
                true,
                "success",
                repayment
        );
        return ResponseEntity.ok(res);
    }
}
