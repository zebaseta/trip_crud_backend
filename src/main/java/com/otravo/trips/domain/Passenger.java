package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
public class Passenger implements CrudEntity<Passenger> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Column(nullable = true, unique = true)
    private String code;*/

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String passport;

    @OneToMany(mappedBy = "passenger")
    private List<Trip> trips;



    public Passenger(String email, String passport) {
        this.email = email;
        this.passport = passport;
    }

    public Passenger(String name, String email, LocalDate dateOfBirth, String passport) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passport = passport;
    }

    @Override
    public void throwErrorIfCreationIsNotOk() throws DomainException {
//        if (code == null) throw new DomainException("Code cannot be null");
        if (name == null) throw new DomainException("Name cannot be null");
        if (email == null) throw new DomainException("Email cannot be null");
        if (dateOfBirth == null) throw new DomainException("Date of birth cannot be null");
        if (passport == null) throw new DomainException("Passport cannot be null");
    }

    @Override
    public void throwErrorIfUpdatingIsNotOk() throws DomainException {
        if ( name == null && email == null && dateOfBirth == null && passport == null)
            throw new DomainException("No attributes were modified");
        if (name == null && email == null && dateOfBirth == null && passport == null)
            throw new DomainException("No attributes were modified");
    }

    @Override
    public void updateFromEntity(Passenger newData) {
        if (newData.getName() != null) name = newData.getName();
        if (newData.getEmail() != null) email = newData.getEmail();
        if (newData.getDateOfBirth() != null) dateOfBirth = newData.getDateOfBirth();
        if (newData.getPassport() != null) passport = newData.getPassport();
    }

    @Override
    public String getSystemIdInStringFormat() {
        return getId().toString();}
}
