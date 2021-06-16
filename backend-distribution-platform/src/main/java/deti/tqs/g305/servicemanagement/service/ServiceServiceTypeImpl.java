package deti.tqs.g305.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import deti.tqs.g305.servicemanagement.model.*;

import deti.tqs.g305.servicemanagement.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import deti.tqs.g305.servicemanagement.repository.ClientRepository;


import org.springframework.beans.factory.annotation.Autowired;


/**
 * ServiceServiceTypeImpl
 */
@Service
@Transactional
public class ServiceServiceTypeImpl implements ServiceServiceType {

    Logger logger = LoggerFactory.getLogger(ServiceService.class); // to log everything

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public Optional<ServiceType> addServiceType(ServiceType st){
        Optional<ServiceType> optSt = serviceTypeRepository.findByName(st.getName());
        if(optSt.isEmpty()){
            ServiceType st_final = new ServiceType(st.getName(), st.getHasExtras());
            serviceTypeRepository.save(st_final);
        }
        return Optional.empty();
    } 

    public List<ServiceType> getServiceTypes(ServiceType st){
        return null;
    }
    
}