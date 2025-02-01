package org.logisticcompany.service;

import org.logisticcompany.model.Office;
import org.logisticcompany.model.dto.OfficeDto;

import java.util.List;

public interface OfficeService {
    Office createOffice(OfficeDto officeDto);

    List<OfficeDto> getOffices();

    Office updateOffice(OfficeDto officeDto, Long id);

    void deleteOffice(Long id);
}
