package project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerVo {
	private int id;
	@JsonProperty("customer_name")
    private String customerName;
	
	@JsonProperty("check_in")
    private String checkIn;
	@JsonProperty("check_out")
    private String checkOut;
    private String status;

    public int getId(){
        return id;
    }
    public String getCustomerName(){
        return customerName;
    }
    public String getCheckIn(){
        return checkIn;
    }
    public String getCheckOut(){
        return checkOut;
    }

    public String getStatus(){
        return status;
    }
}
