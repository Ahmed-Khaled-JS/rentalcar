package com.example.demo.Model;

//import java.util.List;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")//lowercase
public class Vehicle {

    @Id
   @Column(name="car_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer carTd;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_price")
    private Integer carPricePerDay;

   
    @Column(name = "isAvaliable")
    private Boolean isAvaliable ;


    @Column(name="latitude")
    private long latitude;

    @Column(name="longtiude")
    private long longtiude;


    @Column(name = "car_coverImage")
    private String carCoverImage;

    @Column(name = "carLicense")
    private String carLicense;


    @Column(name = "car_address")
    private String carAddress;

    @Column(name = "no_of_passengers")
    private String noOfPassengers;


    @Column(name = "car_Desc")
    private String carDesc;

    @ManyToOne
    @JoinColumn(name = "serviceProvider_id")
    private User user;
    

 
    
    public Vehicle(){

    }

    
    public Vehicle( String carName, Integer carPricePerDay, Boolean isAvaliable , long latitude, long longtiude,
            User user) {
       
        this.carName = carName;
        this.carPricePerDay = carPricePerDay;
        this.isAvaliable=isAvaliable;
        this.latitude = latitude;
        this.longtiude = longtiude;
        this.user = user;
    }

    public String getCarName() {
        return carName;
    }


    public void setCarName(String carName) {
        this.carName = carName;
    }


    public Boolean getIsAvaliable() {
        return isAvaliable;
    }


    public void setIsAvaliable(Boolean isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public String getcarName() {
        return carName;
    }


    public void setcarName(String carName) {
        this.carName = carName;
    }



    public Integer getCarPricePerDay() {
        return carPricePerDay;
    }


    public void setCarPricePerDay(Integer carPricePerDay) {
        this.carPricePerDay = carPricePerDay;
    }


    public String getCarCoverImage() {
        return carCoverImage;
    }


    public void setCarCoverImage(String carCoverImage) {
        this.carCoverImage = carCoverImage;
    }


    public String getCarLicense() {
        return carLicense;
    }


    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }


    public String getCarAddress() {
        return carAddress;
    }


    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
    }


    public String getNoOfPassengers() {
        return noOfPassengers;
    }


    public void setNoOfPassengers(String noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }


    public String getCarDesc() {
        return carDesc;
    }


    public void setCarDesc(String carDesc) {
        this.carDesc = carDesc;
    }


    public long getLatitude() {
        return latitude;
    }


    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }


    public long getLongtiude() {
        return longtiude;
    }


    public void setLongtiude(long longtiude) {
        this.longtiude = longtiude;
    }



    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }


    public Integer getCarTd() {
        return carTd;
    }


    public void setCarTd(Integer carTd) {
        this.carTd = carTd;
    }


   


}