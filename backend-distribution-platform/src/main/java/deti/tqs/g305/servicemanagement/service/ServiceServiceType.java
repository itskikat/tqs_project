package deti.tqs.g305.servicemanagement.service;

import deti.tqs.g305.servicemanagement.model.ServiceType;

import java.util.Optional;
import java.util.List;

/**
 * ServiceServiceType
 */
public interface ServiceServiceType {

    public Optional<ServiceType> addServiceType(ServiceType st); 
    public List<ServiceType> getServiceTypes(ServiceType st);
}