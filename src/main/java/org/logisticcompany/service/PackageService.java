package org.logisticcompany.service;

import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.Package;

import java.util.List;

public interface PackageService {
    Package createPackage(PackageDto packageDto);

    List<PackageDto> getPackages();

    Package updatePackage(PackageDto packageDto, Long id);

    void deletePackage(Long id);

    List<PackageDto> getAllRegisteredPackages();

    List<PackageDto> getAllRegisteredPackagesByReceiver(Long receiverId);

    List<PackageDto> getAllRegisteredNotDeliveredPackages();

    List<PackageDto> getAllSentNotDeliveredPackages();

    List<PackageDto> getAllPackagesSentByClient(Long clientId);

    List<PackageDto> getAllPackagesReceivedByClient(Long clientId);
}
