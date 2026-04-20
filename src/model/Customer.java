package model;

public class Customer {

    private String name;
    private String email;
    private String mobile;
    private String address;
    private String username;
private String password;


    public Customer(String name, String email, String mobile, String address,String username, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    
    public Customer(String name, String email, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }
  
      public void setName(String name) {
        this.name = name;
    } 

    public String getName() {
        return name;
    }

     public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

     public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

      public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }   

public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
}
