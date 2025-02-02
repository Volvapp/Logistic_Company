package org.logisticcompany;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.logisticcompany.model.Office;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.service.PackageServiceModel;
import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.repository.OfficeRepository;
import org.logisticcompany.service.Impl.PackageServiceImpl;
import org.logisticcompany.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PackageServiceTests {

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private ModelMapper modelMapper;

    private PackageServiceImpl packageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        packageService = new PackageServiceImpl(packageRepository, userRepository, officeRepository, modelMapper, null);
    }


    @Test
    void testCreatePackage_UserNotFound() {
        // Arrange
        PackageServiceModel packageServiceModel = new PackageServiceModel();
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> packageService.createPackage(packageServiceModel, username));
    }

    @Test
    void testUpdatePackage() {
        // Arrange
        PackageDto packageDto = new PackageDto();
        packageDto.setAddress("456 Oak Rd");
        Long packageId = 1L;
        Package existingPackage = new Package();
        existingPackage.setId(packageId);

        when(packageRepository.findById(packageId)).thenReturn(Optional.of(existingPackage));
        when(packageRepository.save(existingPackage)).thenReturn(existingPackage);

        // Act
        Package updatedPackage = packageService.updatePackage(packageDto, packageId);

        // Assert
        assertNotNull(updatedPackage);
        assertEquals(packageId, updatedPackage.getId());
        verify(packageRepository, times(1)).save(existingPackage);
    }

    @Test
    void testUpdatePackage_PackageNotFound() {
        // Arrange
        PackageDto packageDto = new PackageDto();
        Long packageId = 1L;

        when(packageRepository.findById(packageId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class, () -> packageService.updatePackage(packageDto, packageId));
    }

    @Test
    void testDeletePackage() {
        // Arrange
        Long packageId = 1L;

        doNothing().when(packageRepository).deleteById(packageId);

        // Act
        packageService.deletePackage(packageId);

        // Assert
        verify(packageRepository, times(1)).deleteById(packageId);
    }

    @Test
    void testGetPackages() {
        // Arrange
        Package package1 = new Package();
        Package package2 = new Package();
        when(packageRepository.findAll()).thenReturn(List.of(package1, package2));
        when(modelMapper.map(any(Package.class), eq(PackageDto.class))).thenReturn(new PackageDto());

        // Act
        List<PackageDto> packageDtos = packageService.getPackages();

        // Assert
        assertEquals(2, packageDtos.size());
    }

    @Test
    void testCalculatePrice_WeightOver20() {
        // Arrange
        Package pack = new Package();
        pack.setWeight(25.0);
        pack.setPrice(100.0);

        when(officeRepository.findAll()).thenReturn(List.of(mock(Office.class)));  // Mock the office addresses

        // Act
        Double price = packageService.calculatePrice(pack);

        // Assert
        assertTrue(price > 100.0);  // Price should be modified due to the weight tax
    }

    @Test
    void testCalculatePrice_AddressNotInOffices() {
        // Arrange
        Package pack = new Package();
        pack.setWeight(10.0);
        pack.setPrice(100.0);
        pack.setAddress("1234 Unknown Address");

        when(officeRepository.findAll()).thenReturn(List.of(mock(Office.class)));  // Mock the office addresses

        // Act
        Double price = packageService.calculatePrice(pack);

        // Assert
        assertTrue(price > 100.0);  // Price should be modified due to the address tax
    }

    @Test
    void testGetAllRegisteredPackages() {
        // Arrange
        Package package1 = new Package();
        Package package2 = new Package();
        when(packageRepository.findAllByType(PackageType.ACCEPTED)).thenReturn(List.of(package1, package2));
        when(modelMapper.map(any(Package.class), eq(PackageDto.class))).thenReturn(new PackageDto());

        // Act
        String result = packageService.getAllRegisteredPackages();

        // Assert
        assertNotNull(result);
    }
}
