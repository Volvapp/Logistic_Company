package org.logisticcompany.service.Impl;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.Office;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.dto.LogisticCompanyDto;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.service.LogisticCompanyService;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LogisticCompanyServiceImpl implements LogisticCompanyService {
    private static final Logger log = LoggerFactory.getLogger(LogisticCompanyServiceImpl.class);
    private final LogisticCompanyRepository logisticCompanyRepository;
    private final ModelMapper modelMapper;

    public LogisticCompanyServiceImpl(LogisticCompanyRepository logisticCompanyRepository, ModelMapper modelMapper) {
        this.logisticCompanyRepository = logisticCompanyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogisticCompany createCompany(LogisticCompanyDto companyDto) {
        LogisticCompany company = modelMapper.map(companyDto, LogisticCompany.class);

        logisticCompanyRepository.save(company);
        log.info(String.format("Company %s created", company.getName()));
        return company;
    }

    @Override
    public List<LogisticCompanyDto> getCompanies() {
        List<LogisticCompany> companies = this.logisticCompanyRepository.findAll();
        List<LogisticCompanyDto> companiesDto = new ArrayList<>();

        for (LogisticCompany company : companies) {
            companiesDto.add(this.modelMapper.map(company, LogisticCompanyDto.class));
        }

        return companiesDto;
    }

    @Override
    public LogisticCompany updateCompany(LogisticCompanyDto companyDto, Long id) {
        LogisticCompany logisticCompany = this.logisticCompanyRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("No company found with id: %d", id)));

        this.modelMapper.map(companyDto, logisticCompany);

        this.logisticCompanyRepository.save(logisticCompany);
        log.info(String.format("Company %s updated", logisticCompany.getName()));
        return logisticCompany;
    }

    @Override
    public void deleteCompany(Long id) {
        this.logisticCompanyRepository.deleteById(id);
        log.info(String.format("Company with id: %d deleted", id));
    }

    @Override
    public Double getRevenueForTimePeriod(Long companyId, LocalDate start, LocalDate end) {
        return this.logisticCompanyRepository.findById(companyId)
                .map(logisticCompany -> logisticCompany.getOffices().stream()
                        .flatMap(office -> office.getPackages().stream())
                        .filter(pack -> !pack.getArrivalDate().isBefore(start) && !pack.getArrivalDate().isAfter(end))
                        .mapToDouble(Package::getPrice)
                        .sum())
                .orElse(null);
    }
}
