/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;

/**
 *
 * @author dompl
 */
public class PersonAdress extends Person implements Comparable<PersonAdress>,Serializable {
    private String index;
    private String firstName;
    private String lastName;
    private String adress;
    private String city;
    public char type='a';
    
    private PersonAdress(){
    
    }
    public PersonAdress(String index, String firstName, String lastName,String adress, String city){
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.city = city;
    }
    @Override
    public int compareTo(PersonAdress o) {
        if (this.firstName.compareTo(o.firstName)==0){
            if (this.lastName.compareTo(o.lastName)==0){
                return this.city.compareTo(o.city);
            }
            else{
                return this.lastName.compareTo(o.lastName);
            }
        }
        return this.firstName.compareTo(o.firstName);
    }
    @Override
     public String printPerson() {
      return (this.getIndex() + " " + this.getFirstName() + " " + this.getLastName() + " " + this.getAdress() + " " + this.getCity() + "\n");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    
}
