package com.example.CarRental.service;

import com.example.CarRental.dto.CarDto;
import com.example.CarRental.entity.Car;
import com.example.CarRental.entity.CarModel;
import com.example.CarRental.entity.Location;
import com.example.CarRental.exception.ResourceNotFoundException;
import com.example.CarRental.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarDto createCar(CarDto carDto) {
        Car car = new Car();
        setCarProperties(car, carDto);

        Car savedCar = carRepository.save(car);
        log.info("Created car: {}", savedCar);
        return mapToDto(savedCar);
    }

    public Page<CarDto> getAllCars(Pageable pageable) {
        Page<Car> carPage = carRepository.findAll(pageable);
        log.info("Retrieved all cars with pagination");
        return carPage.map(this::mapToDto);
    }

    public CarDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        log.info("Retrieved car: {}", car);
        return mapToDto(car);
    }

    public CarDto updateCar(Long id, CarDto carDto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car not found with id {}", id);
                    return new ResourceNotFoundException("Car not found with id " + id);
                });

        setCarProperties(car, carDto);

        Car updatedCar = carRepository.save(car);
        log.info("Updated car: {}", updatedCar);
        return mapToDto(updatedCar);
    }

    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
        log.info("Deleted car: {}", car);
    }

    private void setCarProperties(Car car, CarDto carDto) {
        car.setModel(new CarModel(carDto.getModelId()));
        car.setLocation(new Location(carDto.getLocationId()));
        car.setStatus(carDto.getStatus());
        car.setPricePerHour(carDto.getPricePerHour());
        car.setPricePerDay(carDto.getPricePerDay());
    }

    private CarDto mapToDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .modelId(car.getModel().getId())
                .locationId(car.getLocation().getId())
                .status(car.getStatus())
                .pricePerHour(car.getPricePerHour())
                .pricePerDay(car.getPricePerDay())
                .build();
    }
}
