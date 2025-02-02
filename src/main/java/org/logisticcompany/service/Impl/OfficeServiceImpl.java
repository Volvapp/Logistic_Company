package org.logisticcompany.service.Impl;

import org.logisticcompany.model.Office;
import org.logisticcompany.model.dto.OfficeDto;
import org.logisticcompany.repository.OfficeRepository;
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

    public OfficeServiceImpl(OfficeRepository officeRepository, ModelMapper modelMapper) {
        this.officeRepository = officeRepository;
        this.modelMapper = modelMapper;
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
}