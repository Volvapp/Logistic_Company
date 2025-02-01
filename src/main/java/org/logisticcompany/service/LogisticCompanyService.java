package org.logisticcompany.service;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.dto.LogisticCompanyDto;

import java.util.List;

public interface LogisticCompanyService {
    LogisticCompany createCompany(LogisticCompanyDto companyDto);

    List<LogisticCompanyDto> getCompanies();

    LogisticCompany updateCompany(LogisticCompanyDto companyDto, Long id);

    void deleteCompany(Long id);
}
