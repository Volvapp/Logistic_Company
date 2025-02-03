package org.logisticcompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logisticcompany.model.Office;
import org.logisticcompany.model.dto.OfficeDto;
import org.logisticcompany.repository.OfficeRepository;
import org.logisticcompany.service.Impl.OfficeServiceImpl;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfficeServiceTests {

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OfficeServiceImpl officeService;

    private Office office;
    private OfficeDto officeDto;

    @BeforeEach
    void setUp() {
        office = new Office();
        office.setId(1L);
        office.setAddress("123 Test St");

        officeDto = new OfficeDto();
        officeDto.setAddress("123 Test St");
    }

    @Test
    void createOffice_ShouldSaveAndReturnOffice() {
        when(modelMapper.map(officeDto, Office.class)).thenReturn(office);
        when(officeRepository.save(office)).thenReturn(office);

        Office createdOffice = officeService.createOffice(null, "");

        assertNotNull(createdOffice);
        assertEquals("123 Test St", createdOffice.getAddress());
        verify(officeRepository, times(1)).save(office);
    }

    @Test
    void getOffices_ShouldReturnListOfOffices() {
        when(officeRepository.findAll()).thenReturn(List.of(office));
        when(modelMapper.map(office, OfficeDto.class)).thenReturn(officeDto);

        List<OfficeDto> offices = officeService.getOffices();

        assertEquals(1, offices.size());
        assertEquals("123 Test St", offices.get(0).getAddress());
        verify(officeRepository, times(1)).findAll();
    }

    @Test
    void updateOffice_ShouldUpdateAndReturnOffice() {
        when(officeRepository.findById(1L)).thenReturn(Optional.of(office));
        when(officeRepository.save(office)).thenReturn(office);

        Office updatedOffice = officeService.updateOffice(officeDto, 1L);

        assertNotNull(updatedOffice);
        assertEquals("123 Test St", updatedOffice.getAddress());
        verify(officeRepository, times(1)).save(office);
    }

    @Test
    void updateOffice_ShouldThrowExceptionWhenOfficeNotFound() {
        when(officeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> officeService.updateOffice(officeDto, 1L));
    }

    @Test
    void deleteOffice_ShouldDeleteOffice() {
        doNothing().when(officeRepository).deleteById(1L);

        officeService.deleteOffice(1L);

        verify(officeRepository, times(1)).deleteById(1L);
    }
}
