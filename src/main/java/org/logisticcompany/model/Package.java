package org.logisticcompany.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "packages")
public class Package extends BaseEntity{
    @ManyToOne
    private UserEntity sender;
    @ManyToOne
    private UserEntity receiver;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "weight", nullable = false)
    private Double weight;
    @Column(name = "price")
    private Double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PackageType type;
}
