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
public class PersonMail extends Person implements Comparable<PersonMail>,Serializable{
    private String index;
    private String firstName;
    private String lastName;
    private String mail;
    private String ggNumber;
    public char type='m';
    
    private PersonMail(){
        
    }
    public PersonMail(String index, String firstName, String lastName, String mail, String ggNumber){
        this.index = index;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.ggNumber = ggNumber;
    }
   
    @Override
    public int compareTo(PersonMail o) {
        if (this.firstName.compareTo(o.firstName)==0){
            return this.lastName.compareTo(o.lastName);
        }
        return this.firstName.compareTo(o.firstName);
    }
    @Override
     public String printPerson() {
      return (this.getIndex() + " " + this.getFirstName() + " " + this.getLastName() + " " + this.getMail() + " " + this.getGgNumber() + "\n");
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGgNumber() {
        return ggNumber;
    }

    public void setGgNumber(String ggNumber) {
        this.ggNumber = ggNumber;
    }
}
