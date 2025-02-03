package org.logisticcompany.service;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.dto.LogisticCompanyDto;
import org.logisticcompany.model.service.LogisticCompanyServiceModel;
import org.logisticcompany.model.service.PackageServiceModel;

import java.time.LocalDate;
import java.util.List;

public interface LogisticCompanyService {
    LogisticCompany createCompany(LogisticCompanyServiceModel logisticCompanyServiceModel, String userName);

    List<LogisticCompanyDto> getCompanies();

    LogisticCompany updateCompany(LogisticCompanyDto companyDto, Long id);

    void deleteCompany(Long id);

    Double getRevenueForTimePeriod(Long companyId, LocalDate start, LocalDate end);

    void initializeLogisticCompanies();
}
