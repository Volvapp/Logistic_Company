package org.logisticcompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.Office;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.dto.LogisticCompanyDto;
import org.logisticcompany.model.service.LogisticCompanyServiceModel;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.service.Impl.LogisticCompanyServiceImpl;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogisticCompanyServiceTests {

    @Mock
    private LogisticCompanyRepository logisticCompanyRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LogisticCompanyServiceImpl logisticCompanyService;

    private LogisticCompany company;
    private LogisticCompanyDto companyDto;

    private LogisticCompanyServiceModel logisticCompanyServiceModel;

    @BeforeEach
    void setUp() {
        company = new LogisticCompany();
        company.setId(1L);
        company.setName("Test Company");

        companyDto = new LogisticCompanyDto();
        companyDto.setName("Test Company");

        logisticCompanyServiceModel = new LogisticCompanyServiceModel();
        logisticCompanyServiceModel.setName("Test Company");
    }


    @Test
    void getCompanies_ShouldReturnListOfCompanies() {
        when(logisticCompanyRepository.findAll()).thenReturn(List.of(company));
        when(modelMapper.map(company, LogisticCompanyDto.class)).thenReturn(companyDto);

        List<LogisticCompanyDto> result = logisticCompanyService.getCompanies();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(companyDto.getName(), result.get(0).getName());
    }

    @Test
    void updateCompany_ShouldUpdateAndReturnCompany() {
        when(logisticCompanyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(logisticCompanyRepository.save(company)).thenReturn(company);

        LogisticCompany result = logisticCompanyService.updateCompany(companyDto, 1L);

        assertNotNull(result);
        assertEquals(company.getName(), result.getName());
        verify(logisticCompanyRepository, times(1)).save(company);
    }

    @Test
    void updateCompany_ShouldThrowException_WhenCompanyNotFound() {
        when(logisticCompanyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> logisticCompanyService.updateCompany(companyDto, 1L));
    }

    @Test
    void deleteCompany_ShouldDeleteCompany() {
        doNothing().when(logisticCompanyRepository).deleteById(1L);

        logisticCompanyService.deleteCompany(1L);

        verify(logisticCompanyRepository, times(1)).deleteById(1L);
    }

    @Test
    void getRevenueForTimePeriod_ShouldReturnRevenue() {
        Office office = new Office();
        Package package1 = new Package();
        package1.setPrice(100.0);
        package1.setArrivalDate(LocalDate.of(2024, 1, 10));
        Package package2 = new Package();
        package2.setPrice(200.0);
        package2.setArrivalDate(LocalDate.of(2024, 1, 15));
        office.setPackages(List.of(package1, package2));
        company.setOffices(List.of(office));

        when(logisticCompanyRepository.findById(1L)).thenReturn(Optional.of(company));

        String revenue = logisticCompanyService.getRevenueForTimePeriod(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        ;
        assertEquals("300.0", revenue);
    }


}