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
        // Convert DTO to entity
        LogisticCompany company = modelMapper.map(companyDto, LogisticCompany.class);

        // Save company to the database
        logisticCompanyRepository.save(company);

        // Log creation info
        log.info(String.format("Company %s created", company.getName()));

        return company;
    }

    @Override
    public List<LogisticCompanyDto> getCompanies() {
        // Retrieve all companies from the database
        List<LogisticCompany> companies = this.logisticCompanyRepository.findAll();

        // Convert entities to DTOs
        List<LogisticCompanyDto> companiesDto = new ArrayList<>();
        for (LogisticCompany company : companies) {
            companiesDto.add(this.modelMapper.map(company, LogisticCompanyDto.class));
        }

        return companiesDto;
    }

    @Override
    public LogisticCompany updateCompany(LogisticCompanyDto companyDto, Long id) {
        // Find the company by ID, throw exception if not found
        LogisticCompany logisticCompany = this.logisticCompanyRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("No company found with id: %d", id)));

        // Update the entity with new values from DTO
        this.modelMapper.map(companyDto, logisticCompany);

        // Save updated company to the database
        this.logisticCompanyRepository.save(logisticCompany);

        // Log update info
        log.info(String.format("Company %s updated", logisticCompany.getName()));

        return logisticCompany;
    }

    @Override
    public void deleteCompany(Long id) {
        // Delete company by ID
        this.logisticCompanyRepository.deleteById(id);

        // Log deletion info
        log.info(String.format("Company with id: %d deleted", id));
    }

    @Override
    public Double getRevenueForTimePeriod(Long companyId, LocalDate start, LocalDate end) {
        // Find company by ID and calculate revenue for the given period
        return this.logisticCompanyRepository.findById(companyId)
                .map(logisticCompany -> logisticCompany.getOffices().stream()
                        // Retrieve all packages from company offices
                        .flatMap(office -> office.getPackages().stream())
                        // Filter packages within the given date range
                        .filter(pack -> !pack.getArrivalDate().isBefore(start) && !pack.getArrivalDate().isAfter(end))
                        // Sum the package prices to get total revenue
                        .mapToDouble(Package::getPrice)
                        .sum())
                .orElse(null);
    }
}