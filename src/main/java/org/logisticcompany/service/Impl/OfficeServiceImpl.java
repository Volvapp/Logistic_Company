package org.logisticcompany.service.Impl;

import org.logisticcompany.model.Office;
import org.logisticcompany.model.dto.OfficeDto;
import org.logisticcompany.repository.OfficeRepository;
import org.logisticcompany.service.OfficeService;
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
        Office office = this.modelMapper.map(officeDto, Office.class);

        this.officeRepository.save(office);
        log.info(String.format("Office with address: %s created", office.getAddress()));
        return office;

    }

    @Override
    public List<OfficeDto> getOffices() {
        List<Office> offices = this.officeRepository.findAll();
        List<OfficeDto> officeDtos = new ArrayList<>();

        for (Office office : offices) {
            officeDtos.add(this.modelMapper.map(office, OfficeDto.class));
        }
        return officeDtos;
    }

    @Override
    public Office updateOffice(OfficeDto officeDto, Long id) {

        Office office = this.officeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        this.modelMapper.map(officeDto, office);

        this.officeRepository.save(office);
        log.info(String.format("Office with address: %s updated", office.getAddress()));
        return office;

    }


    @Override
    public void deleteOffice(Long id) {
        this.officeRepository.deleteById(id);
        log.info(String.format("Office with id: %d deleted", id));
    }
}
