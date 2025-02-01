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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<PackageDto> getAllRegisteredPackages() {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> registered = this.packageRepository.findAllByType(PackageType.ACCEPTED);

        for (Package pack : registered) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos;
    }

    @Override
    public List<PackageDto> getAllRegisteredPackagesByReceiver(Long receiverId) {

        UserEntity receiver = this.userRepository
                .findById(receiverId)
                .orElseThrow(() -> new RuntimeException(String.format("Receiver with id %d not found", receiverId)));
        Set<Role> roles = receiver.getRoles().stream().map(RoleEntity::getRole).collect(Collectors.toSet());

        if (!roles.contains(Role.OFFICE_EMPLOYEE)) {
            log.error("Receiver is not an office employee");
            return List.of();
        }

        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> allByStateAndTypeAndReceiverId = this.packageRepository
                .findAllByStateAndTypeAndReceiver_Id(State.NOT_DELIVERED, PackageType.ACCEPTED, receiverId);

        for (Package pack : allByStateAndTypeAndReceiverId) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }


        return packageDtos;
    }

    @Override
    public List<PackageDto> getAllRegisteredNotDeliveredPackages() {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> allByStateAndType = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.ACCEPTED);

        for (Package pack : allByStateAndType) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos;
    }

    @Override
    public List<PackageDto> getAllSentNotDeliveredPackages() {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.SENT);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }


        return packageDtos;
    }

    @Override
    public List<PackageDto> getAllPackagesSentByClient(Long clientId) {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByTypeAndSender_Id(PackageType.SENT, clientId);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }
        return packageDtos;
    }

    @Override
    public List<PackageDto> getAllPackagesReceivedByClient(Long clientId) {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByTypeAndReceiver_Id(PackageType.ACCEPTED, clientId);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos;
    }
}
