package org.logisticcompany.service.Impl;

import org.logisticcompany.model.Office;
import org.logisticcompany.model.RoleEntity;
import org.logisticcompany.model.UserEntity;
import org.logisticcompany.model.dto.PackageDto;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.Role;
import org.logisticcompany.model.enums.State;
import org.logisticcompany.repository.OfficeRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PackageServiceImpl implements PackageService {
    private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);
    private static final Double TAX = 1.15;
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    public PackageServiceImpl(PackageRepository packageRepository, UserRepository userRepository, OfficeRepository officeRepository, ModelMapper modelMapper) {
        this.packageRepository = packageRepository;
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
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
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAll();

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos;
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
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> registered = this.packageRepository.findAllByType(PackageType.ACCEPTED);

        for (Package pack : registered) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();

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
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> allByStateAndTypeAndReceiverId = this.packageRepository
                .findAllByStateAndTypeAndReceiver_Id(State.NOT_DELIVERED, PackageType.ACCEPTED, receiverId);


        for (Package pack : allByStateAndTypeAndReceiverId) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();
    }

    @Override
    public String getAllRegisteredNotDeliveredPackages() {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> allByStateAndType = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.ACCEPTED);


        for (Package pack : allByStateAndType) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();
    }

    @Override
    public String getAllSentNotDeliveredPackages() {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByStateAndType(State.NOT_DELIVERED, PackageType.SENT);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();
    }

    @Override
    public String getAllPackagesSentByClient(Long clientId) {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByTypeAndSender_Id(PackageType.SENT, clientId);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();
    }

    @Override
    public String getAllPackagesReceivedByClient(Long clientId) {
        List<PackageDto> packageDtos = new ArrayList<>();
        List<Package> packages = this.packageRepository.findAllByTypeAndReceiver_Id(PackageType.ACCEPTED, clientId);

        for (Package pack : packages) {
            packageDtos.add(this.modelMapper.map(pack, PackageDto.class));
        }

        return packageDtos.toString();
    }

    @Override
    public Double calculatePrice(Long packageId) {
        Package pack = this.packageRepository.findById(packageId).orElseThrow(() -> new RuntimeException("There is no such package"));
        List<Office> offices = this.officeRepository.findAll();
        List<String> addresses = offices.stream().map(Office::getAddress).collect(Collectors.toList());

        if (!addresses.contains(pack.getAddress())){
            pack.setPrice(pack.getPrice() * TAX);
        }

        return pack.getPrice();
    }

    @Override
    public void initializePackages() {
        UserEntity sender = this.userRepository.findById(2L).get(); // Assuming sender object is initialized
        UserEntity receiver = this.userRepository.findById(4L).get(); // Assuming receiver object is initialized

        Package package1 = new Package(sender, receiver, "123 Main St", 5.0, 100.0, State.DELIVERED, PackageType.SENT);
        this.packageRepository.save(package1);
        Package package2 = new Package(sender, receiver, "456 Oak Rd", 10.0, 150.0, State.NOT_DELIVERED, PackageType.SENT);
        this.packageRepository.save(package2);
        Package package3 = new Package(sender, receiver, "789 Pine Ave", 2.0, 50.0, State.DELIVERED, PackageType.ACCEPTED);
        this.packageRepository.save(package3);
        Package package4 = new Package(sender, receiver, "101 Maple Blvd", 8.0, 200.0, State.NOT_DELIVERED, PackageType.ACCEPTED);
        this.packageRepository.save(package4);

        System.out.println(this.getAllRegisteredPackages());
        System.out.println(this.getAllRegisteredNotDeliveredPackages());
        System.out.println(this.getAllPackagesReceivedByClient(3L));
        System.out.println(this.getAllPackagesReceivedByClient(2L));
        System.out.println(this.getAllPackagesSentByClient(3L));
        System.out.println(this.getAllPackagesSentByClient(2L));
        System.out.println(this.getAllRegisteredPackagesByReceiver(4L));
        System.out.println(this.getAllRegisteredPackagesByReceiver(2L));
    }
}
