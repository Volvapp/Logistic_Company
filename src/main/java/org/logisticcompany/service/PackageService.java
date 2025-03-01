package org.logisticcompany.service;

import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.Package;
import org.logisticcompany.model.service.PackageServiceModel;
import org.logisticcompany.model.view.ClientPackageDetailsView;

import java.util.List;

public interface PackageService {
    Package createPackage(PackageServiceModel packageServiceModel, String userName);

    List<PackageDto> getPackages();

    Package updatePackage(PackageDto packageDto, Long id);

    void deletePackage(Long id);

    String getAllRegisteredPackages();

    String getAllRegisteredPackagesByReceiver(Long receiverId);

    String getAllRegisteredNotDeliveredPackages();

    String getAllSentNotDeliveredPackages();

    String getAllPackagesSentByClient(Long clientId);

    String getAllPackagesReceivedByClient(Long clientId);

    Double calculatePrice(Package pack);

    void acceptPackage(Long packageId, String employeeUsername);

    void deliverPackage(Long packageId, String employeeUsername);

    void initializePackages();

    List<ClientPackageDetailsView> findAllClientPackagesDetails(String username);

    List<ClientPackageDetailsView> findAllEmployeePackagesDetails();
}
