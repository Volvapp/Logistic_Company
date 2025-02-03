package org.logisticcompany.service.Impl;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.LogisticCompanyDto;
import org.logisticcompany.model.service.LogisticCompanyServiceModel;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.repository.OfficeRepository;
import org.logisticcompany.repository.UserRepository;
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
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public LogisticCompanyServiceImpl(LogisticCompanyRepository logisticCompanyRepository, ModelMapper modelMapper, OfficeRepository officeRepository, UserRepository userRepository) {
        this.logisticCompanyRepository = logisticCompanyRepository;
        this.modelMapper = modelMapper;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LogisticCompany createCompany(LogisticCompanyServiceModel logisticCompanyServiceModel, String username) {
        // Convert service model to entity
        LogisticCompany logisticCompany = modelMapper.map(logisticCompanyServiceModel, LogisticCompany.class);

        logisticCompany.setRevenue(0.0);
        logisticCompany.setOffices(new ArrayList<>());
        logisticCompany.setUserEntities(new ArrayList<>());

        // Save company to the database
        logisticCompanyRepository.save(logisticCompany);

        // Log creation info
        log.info(String.format("Logistic Company %s created", logisticCompany.getName()));

        return logisticCompany;
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
    public String getRevenueForTimePeriod(Long companyId, LocalDate start, LocalDate end) {
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
                .orElse(null).toString();
    }

    @Override
    public void initializeLogisticCompanies() {
        UserEntity userEntityOfficeEmployee = userRepository.findById(3L).get();
        UserEntity userEntityCourierEmployee = userRepository.findById(4L).get();

        LogisticCompany logisticCompany = new LogisticCompany("Speedy", 0.0);

        logisticCompany.setUserEntities(List.of(userEntityOfficeEmployee, userEntityCourierEmployee));

        logisticCompanyRepository.save(logisticCompany);
    }
}