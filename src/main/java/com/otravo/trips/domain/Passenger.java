package com.otravo.trips.domain;

import com.otravo.trips.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
public class Passenger implements AbmEntity<Passenger> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String passport;


    public Passenger(String code, String name, String email, LocalDate dateOfBirth, String passport) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passport = passport;
    }

    @Override
    public void throwErrorIfCreationIsNotOk() throws DomainException {
        if (code == null) throw new DomainException("Code cannot be null");
        if (name == null) throw new DomainException("Name cannot be null");
        if (email == null) throw new DomainException("Email cannot be null");
        if (dateOfBirth == null) throw new DomainException("Date of birth cannot be null");
        if (passport == null) throw new DomainException("Passport cannot be null");
    }

    @Override
    public void throwErrorIfUpdatingIsNotOk() throws DomainException {
        if (code == null && name == null && email==null && dateOfBirth==null && passport==null)
            throw new DomainException("No attributes were modified");
    }

    @Override
    public void updateFromEntity(Passenger newData) {
        if (newData.getCode() != null) code = newData.getCode();
        if (newData.getName() != null) name = newData.getName();
        if (newData.getEmail() != null) email = newData.getEmail();
        if (newData.getDateOfBirth() != null) dateOfBirth = newData.getDateOfBirth();
        if (newData.getPassport() != null)  passport = newData.getPassport();
    }

    @Override
    public String getSystemIdInStringFormat() {
        return getCode();
    }
}
