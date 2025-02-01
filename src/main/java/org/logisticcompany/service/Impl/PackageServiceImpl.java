package org.logisticcompany.service.Impl;

import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.model.enums.State;
import org.logisticcompany.repository.PackageRepository;
import org.logisticcompany.repository.UserRepository;
import org.logisticcompany.service.PackageService;
import org.modelmapper.ModelMapper;
import org.logisticcompany.model.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {
    private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PackageServiceImpl(PackageRepository packageRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Package createPackage(PackageDto packageDto) {
        Package pack = this.modelMapper.map(packageDto, Package.class);

        this.packageRepository.save(pack);
        log.info("Package created");

        return pack;
    }

    @Override
    public List<PackageDto> getPackages() {
        return List.of();
    }

    @Override
    public Package updatePackage(PackageDto packageDto, Long id) {
        Package pack = this.packageRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Package with id %d not found", id)));
        this.modelMapper.map(packageDto, pack);
        this.packageRepository.save(pack);
        log.info(String.format("Package with id %d updated", pack.getId()));
        return pack;
    }

    @Override
    public void deletePackage(Long id) {
        this.packageRepository.deleteById(id);
        log.info(String.format("Package with id %d deleted", id));
    }

    @Override
    public String getAllRegisteredPackages() {

        List<Package> registered = this.packageRepository.findAllByType(PackageType.ACCEPTED);

        return registered.toString();
    }

    @Override
    public String getAllRegisteredPackagesByReceiver(Long receiverId) {

        UserEntity receiver = this.userRepository
                .findById(receiverId)
                .orElseThrow(() -> new RuntimeException(String.format("Receiver with id %d not found", receiverId)));
        Set<Role> roles = receiver.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toSet());

        if (!roles.contains(Role.OFFICE_EMPLOYEE)) {
            return "Receiver is not an office employee";
        }
        List<Package> allByStateAndTypeAndReceiverId = this.packageRepository
                .findAllByStateAndTypeAndReceiver_Id(State.NOT_DELIVERED, PackageType.ACCEPTED, receiverId);



        return allByStateAndTypeAndReceiverId.toString();
    }

    @Override
    public String getAllRegisteredNotDeliveredPackages() {
        List<Package> allByStateAndType = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.ACCEPTED);


        return allByStateAndType.toString();
    }

    @Override
    public String getAllSentNotDeliveredPackages() {

        List<Package> packages = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.SENT);

        return packages.toString();
    }

    @Override
    public String getAllPackagesSentByClient(Long clientId) {

        List<Package> packages = this.packageRepository.findAllByTypeAndSender_Id(PackageType.SENT, clientId);

        return packages.toString();
    }

    @Override
    public String getAllPackagesReceivedByClient(Long clientId) {

        List<Package> packages = this.packageRepository.findAllByTypeAndReceiver_Id(PackageType.ACCEPTED, clientId);

        return packages.toString();
    }
}
