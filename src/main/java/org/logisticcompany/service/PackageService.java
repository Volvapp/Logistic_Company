package org.logisticcompany.service;

import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.Package;

import java.util.List;

public interface PackageService {
    Package createPackage(PackageDto packageDto);

    List<PackageDto> getPackages();

    Package updatePackage(PackageDto packageDto, Long id);

    void deletePackage(Long id);

    String getAllRegisteredPackages();

    String getAllRegisteredPackagesByReceiver(Long receiverId);

    String getAllRegisteredNotDeliveredPackages();

    String getAllSentNotDeliveredPackages();

    String getAllPackagesSentByClient(Long clientId);

    String getAllPackagesReceivedByClient(Long clientId);
}
