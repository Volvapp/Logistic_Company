package org.logisticcompany.web;

import org.logisticcompany.model.view.ReportViewModel;
import org.logisticcompany.service.LogisticCompanyService;
import org.logisticcompany.service.PackageService;
import org.logisticcompany.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
public class ReportsRestController {

    private final PackageService packageService;

    private final UserService userService;

    private final LogisticCompanyService logisticCompanyService;

    public ReportsRestController(PackageService packageService, UserService userService, LogisticCompanyService logisticCompanyService) {
        this.packageService = packageService;
        this.userService = userService;
        this.logisticCompanyService = logisticCompanyService;
    }

    @GetMapping("/admin/reports/all-employees")
    public ResponseEntity<ReportViewModel> getAllEmployees(){
        return ResponseEntity.ok(new ReportViewModel(userService.getAllEmployees()));
    }

    @GetMapping("/admin/reports/all-clients")
    public ResponseEntity<ReportViewModel> getAllClients(){
        return ResponseEntity.ok(new ReportViewModel(userService.getAllClients()));
    }

    @GetMapping("/admin/reports/all-packages")
    public ResponseEntity<ReportViewModel> getAllPackages(){
        return ResponseEntity.ok(new ReportViewModel(packageService.getAllRegisteredPackages()));
    }

    @GetMapping("/admin/reports/not-delivered-packages")
    public ResponseEntity<ReportViewModel> getNotDeliveredPackages(){
        return ResponseEntity.ok(new ReportViewModel(packageService.getAllRegisteredNotDeliveredPackages()));
    }

    @GetMapping("/admin/reports/sent-not-delivered")
    public ResponseEntity<ReportViewModel> getSentNotDelivered(){
        return ResponseEntity.ok(new ReportViewModel(packageService.getAllSentNotDeliveredPackages()));
    }

    @GetMapping("/admin/reports/packages-by-client")
    public ResponseEntity<ReportViewModel> getPackagesByClient(){
        return ResponseEntity.ok(new ReportViewModel(packageService.getAllPackagesSentByClient(2L)));
    }

    @GetMapping("/admin/reports/packages-by-receiver")
    public ResponseEntity<ReportViewModel> getPackagesByReceiver(){
        return ResponseEntity.ok(new ReportViewModel(packageService.getAllRegisteredPackagesByReceiver(3L)));
    }

    @GetMapping("/admin/reports/revenue")
    public ResponseEntity<ReportViewModel> getRevenue(){
        return ResponseEntity.ok(new ReportViewModel(logisticCompanyService.getRevenueForTimePeriod(1L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(6))));
    }
}
