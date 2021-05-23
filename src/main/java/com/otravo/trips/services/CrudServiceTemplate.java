package com.otravo.trips.services;

import com.otravo.trips.domain.AbmEntity;
import com.otravo.trips.exceptions.DomainException;
import com.otravo.trips.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Generic class from which those services that want to implement a basic AMB must extend
 * The objective of this class is to avoid repetitive code, and treat common operations in the same way.
 */
@Slf4j
public abstract class CrudServiceTemplate<T extends AbmEntity, ID> {
    protected JpaRepository<T, ID> repository;

    public CrudServiceTemplate(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Method to obtain an entity, given another example entity
     * It is enough to create an entity, initializing it with the attributes with which it is going to search
     *
     * @param example => the Example class is initialized like this => Example e = Example.of (new EntitiyClassToFind (.. params))
     * @return List<Entity>
     **/
    public List<T> findAll(Example<T> example) {
        return repository.findAll(example);
    }


    public List<T> saveAll(List<T> entities) throws DomainException, ServiceException {
        List<T> entitiesBD = new ArrayList<>();
        for (T entity : entities) {
            T entityBD = this.create(entity);
            entitiesBD.add(entityBD);
        }
        return entities;
    }

    /**
     * Method to create an entity
     * @param entityToCreate => is assumed without id, since it will autogenerate
     * @return Entity
     * @throws ServiceException
     **/
    public T create(T entityToCreate) throws ServiceException, DomainException {
        entityToCreate.throwErrorIfCreationIsNotOk();
        throwErrorIfExistInBD(entityToCreate);
        try {
            T entityBD = repository.saveAndFlush(entityToCreate);
            return entityBD;
        } catch (Exception e) {
            String message = "Could not create entity {} ";
            log.error(message + ":" + e.getMessage(), entityToCreate);
            e.printStackTrace();
            throw new ServiceException(message);
        }
    }

    protected abstract Optional<T> findInBDBySystemId(T entity);


    private void throwErrorIfExistInBD(T entityToFind) throws ServiceException {
        Optional<T> entity = findInBDBySystemId(entityToFind);
        if (entity.isPresent())
            throw new ServiceException("Entity with id " + entityToFind.getSystemIdInStringFormat() + " already exists at the base");
    }


    /**
     * Method to update an entity
     * The update can be a patch, or just update, depending on what we implement in the AbmEntity interface
     * @param entityDataToUpdate
     * @return
     * @throws ServiceException
     **/
    public T update(T entityDataToUpdate) throws ServiceException, DomainException {
        entityDataToUpdate.throwErrorIfUpdatingIsNotOk();
        T entityBD = findEntityOrthrowExceptionIfNotExist(entityDataToUpdate);
        try {
            entityBD.updateFromEntity(entityDataToUpdate);
            entityBD = repository.save(entityBD);
            return entityBD;
        } catch (Exception e) {
            throw new ServiceException("Could not update entity with id " + entityDataToUpdate.getSystemIdInStringFormat());
        }
    }

    private T findEntityOrthrowExceptionIfNotExist(T entity) throws ServiceException {
        Optional<T> opEntity = findInBDBySystemId(entity);
        if (!opEntity.isPresent())
            throw new ServiceException("Could not find entity with id " + entity.getSystemIdInStringFormat());
        else return opEntity.get();
    }
}
