package org.logisticcompany.repository;

import org.logisticcompany.model.Package;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findAllByType(PackageType packageType);

    List<Package> findAllByStateAndType(State state, PackageType type);

    List<Package> findAllByStateAndTypeAndReceiver_Id(State state, PackageType type, Long receiverId);

    List<Package> findAllByTypeAndSender_Id(PackageType type, Long senderId);

    List<Package> findAllByTypeAndReceiver_Id(PackageType type, Long receiverId);
}
