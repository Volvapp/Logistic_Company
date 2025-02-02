package org.logisticcompany.service.Impl;

import org.logisticcompany.model.LogisticCompany;
import org.logisticcompany.model.Office;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.OfficeDto;
import org.logisticcompany.model.view.OfficeAddressesViewModel;
import org.logisticcompany.repository.LogisticCompanyRepository;
import org.logisticcompany.repository.OfficeRepository;
import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.OfficeService;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {
    private static final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final LogisticCompanyRepository logisticCompanyRepository;

    public OfficeServiceImpl(OfficeRepository officeRepository, ModelMapper modelMapper, UserRepository userRepository, PackageRepository packageRepository, LogisticCompanyRepository logisticCompanyRepository) {
        this.officeRepository = officeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
        this.logisticCompanyRepository = logisticCompanyRepository;
    }

    @Override
    public Office createOffice(OfficeDto officeDto) {
        // Convert OfficeDto to Office entity
        Office office = this.modelMapper.map(officeDto, Office.class);

        // Save office to the database
        this.officeRepository.save(office);

        // Log office creation
        log.info(String.format("Office with address: %s created", office.getAddress()));

        return office;
    }

    @Override
    public List<OfficeDto> getOffices() {
        // Retrieve all offices from the database
        List<Office> offices = this.officeRepository.findAll();

        // Convert Office entities to OfficeDto
        List<OfficeDto> officeDtos = new ArrayList<>();
        for (Office office : offices) {
            officeDtos.add(this.modelMapper.map(office, OfficeDto.class));
        }

        return officeDtos;
    }

    @Override
    public Office updateOffice(OfficeDto officeDto, Long id) {
        // Find the office by ID, throw an exception if not found
        Office office = this.officeRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Office not found"));

        // Map new values from DTO to the existing entity
        this.modelMapper.map(officeDto, office);

        // Save updated office to the database
        this.officeRepository.save(office);

        // Log office update
        log.info(String.format("Office with address: %s updated", office.getAddress()));

        return office;
    }

    @Override
    public void deleteOffice(Long id) {
        // Delete office by ID
        this.officeRepository.deleteById(id);

        // Log office deletion
        log.info(String.format("Office with id: %d deleted", id));
    }

    @Override
    public void initializeOffices() {
        LogisticCompany logisticCompany = logisticCompanyRepository.findById(1L).get();

        Package packageOne = packageRepository.findById(1L).get();
        Package packageTwo = packageRepository.findById(2L).get();
        Package packageThree = packageRepository.findById(3L).get();
        Package packageFour = packageRepository.findById(4L).get();

        UserEntity userEntityOfficeEmployee = userRepository.findById(3L).get();
        UserEntity userEntityCourierEmployee = userRepository.findById(4L).get();

        Office office = new Office("0885432544", "Plovdiv", logisticCompany);

        office.setUserEntities(List.of(userEntityOfficeEmployee, userEntityCourierEmployee));
        office.setPackages(List.of(packageOne, packageTwo, packageThree, packageFour));

        officeRepository.save(office);

        logisticCompany.getOffices().add(office);

        logisticCompanyRepository.save(logisticCompany);
    }

    @Override
    public List<OfficeAddressesViewModel> findAddresses(String username) {
        List<OfficeAddressesViewModel> officeAddressesViewModels = new ArrayList<>();

        List<Office> officeRepositoryAll = officeRepository.findAll();

        for (Office office : officeRepositoryAll) {
            OfficeAddressesViewModel officeAddressesViewModel = new OfficeAddressesViewModel();

            officeAddressesViewModel.setAddress(office.getAddress());

            officeAddressesViewModels.add(officeAddressesViewModel);
        }

        return officeAddressesViewModels;
    }
}