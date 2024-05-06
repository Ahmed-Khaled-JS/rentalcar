package com.example.vehicleService.aop;

import com.example.vehicleService.dto.VehicleRequest;
import com.example.vehicleService.exceptions.VehicleNotFoundException;
import com.example.vehicleService.repository.MyRepository;
import com.example.vehicleService.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
@RequiredArgsConstructor

public class VehicleIdCheckAspect {


    private final VehicleRepository vehicleRepository ;

    private final MyRepository myRepository ;
    @Before("vehicleIdPointcut(id)")
    public void beforeVehicleId(Integer id) {
        if (myRepository.getVehiclesOfServiceProvider(id)==null) {
            throw new VehicleNotFoundException("Vehicle with id " + id + " does not exist");
        }
    }

    @Before("vehicleRequestAndIdPointcut(vehicleRequest, id,file)")
    public void beforeVehicleRequestAndId(VehicleRequest vehicleRequest, Integer id,MultipartFile file) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with id " + id + " does not exist");
        }
    }



    @Before("vehicleIdPointcut2(id)")
    public void beforeVehicleRequestAndId2(Integer id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle with id " + id + " does not exist");
        }
    }









    @Pointcut("execution(* com.example.vehicleService.services.VehicleService.removeCar(..)) && args(id)")
    public void vehicleIdPointcut2(Integer id) {}

    @Pointcut("execution(* com.example.vehicleService.services.VehicleService.*(..)) && args(id)")
    public void vehicleIdPointcut(Integer id) {}

    @Pointcut("execution(* com.example.vehicleService.services.VehicleService.*(..)) && args(vehicleRequest, id,file)")
    public void vehicleRequestAndIdPointcut(VehicleRequest vehicleRequest, Integer id, MultipartFile file) {}

}





