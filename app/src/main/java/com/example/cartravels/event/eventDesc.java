package com.example.cartravels.event;

public class eventDesc {
    private String eventFrom, eventTo, eventDate, eventVehicle, eventVehicleNo;
    private long seat_Nums;

    public eventDesc(){
    }

    public eventDesc(String eventFrom,  String eventTo, String eventDate, String eventVehicle, String eventVehicleNo, long seat_Nums){
        this.eventFrom=eventFrom;
        this.eventDate=eventDate;
        this.eventTo=eventTo;
        this.eventVehicle=eventVehicle;
        this.seat_Nums=seat_Nums;
        this.eventVehicleNo=eventVehicleNo;
    }

    public String getEventFrom() {
        return eventFrom;
    }
    public void setEventFrom(String eventFrom) {
        this.eventFrom = eventFrom;
    }

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTo() {
        return eventTo;
    }
    public void setEventTo(String eventTo) {
        this.eventTo = eventTo;
    }

    public long getSeat_Nums() {
        return seat_Nums;
    }
    public void setSeat_Nums(long seat_Nums) {
        this.seat_Nums = seat_Nums;
    }

    public String getEventVehicle() {
        return eventVehicle;
    }
    public void setEventVehicle(String eventVehicle) {
        this.eventVehicle = eventVehicle;
    }

    public String getEventVehicleNo() {
        return eventVehicleNo;
    }
    public void setEventVehicleNo(String eventVehicleNo) {
        this.eventVehicleNo = eventVehicleNo;
    }
}
