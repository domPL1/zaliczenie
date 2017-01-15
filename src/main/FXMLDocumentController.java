/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 *
 * @author bjuru
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private MenuItem menu1_1;
    @FXML
    private MenuItem menu1_2;
    @FXML
    private MenuItem menu1_3;
    @FXML
    private MenuItem menu1_4;
    @FXML
    private MenuItem menu2_1;
    @FXML
    private MenuItem menu2_2;
    @FXML
    private MenuItem menu2_3;
    @FXML
    private MenuItem menu3_1;
    @FXML
    private MenuItem menu3_2;
    @FXML
    private TextArea textAera1;
    @FXML
    private TextArea textAera2;
    @FXML
    private TabPane tab;
    ArrayList<PersonAdress> adress = new ArrayList();
    ArrayList<PersonMail> contact = new ArrayList();
    Alert inf;
    Alert alert;
    TextInputDialog entry;
    ObjectInputStream in;
    ObjectOutputStream out;
    FileChooser select;
    PersonMail buffor;
    PersonAdress buffor1;
    Person buffor2;
    SingletonSQL database;
    ResultSet result1;
    ObjectOutputStream out1;
    @FXML
    private void about(){
        Alert asd = new Alert(Alert.AlertType.INFORMATION);
        asd.setTitle("PhoneBook");
        asd.setContentText("To start you need to create or open file. Then you can add,modify or delete entries."
                + " You can also import and export from and to mysql database. To close you have to save file and use close option in menu under File.");
        asd.showAndWait();
    }
    @FXML
    private void newFile(){
        select = new FileChooser();
        FileChooser.ExtensionFilter a = new FileChooser.ExtensionFilter("Plik pbk (*.pbk)","*.pbk");
        select.getExtensionFilters().add(a);
        try {
            out = new ObjectOutputStream(new FileOutputStream((select.showSaveDialog(null))));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         select.getExtensionFilters().remove(a);
         select.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik pbg (*.pbg)","*.pbg"));
        try {
            out1 = new ObjectOutputStream(new FileOutputStream((select.showSaveDialog(null))));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        menu1_1.setDisable(true);
        menu1_2.setDisable(true);
        menu1_4.setDisable(true);
        menu1_3.setDisable(false);
        menu2_1.setDisable(false);
        menu2_2.setDisable(false);
        menu2_3.setDisable(false);
        menu3_1.setDisable(false);
        tab.setVisible(true);
    }
    @FXML
    private void openFile()
    {
        select = new FileChooser();
        FileChooser.ExtensionFilter a = new FileChooser.ExtensionFilter("Plik pbk (*.pbk)","*.pbk");
        select.getExtensionFilters().add(a);
        File plik = select.showOpenDialog(null);
        try {
            in = new ObjectInputStream(new FileInputStream(plik));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            while(true) 
            {
                contact.add((PersonMail)in.readObject());
            }
        }catch (EOFException e) {
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        select = new FileChooser();
        select.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik pbg (*.pbg)","*.pbg"));
        File plik1 = select.showOpenDialog(null);
        try {
            in = new ObjectInputStream(new FileInputStream(plik1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            while(true) 
            {
                adress.add((PersonAdress)in.readObject());
            }
        }catch (EOFException e) {
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(adress, (PersonAdress o1, PersonAdress o2) -> o1.compareTo(o2));
        Collections.sort(contact, (PersonMail o1,PersonMail o2) -> o1.compareTo(o2));
        for (int i=0;i<adress.size();i++){
                 adress.get(i).setIndex(Integer.toString(i));
                 textAera1.appendText(adress.get(i).printPerson());
            }
        for (int i=0;i<contact.size();i++){
                 contact.get(i).setIndex(Integer.toString(i));
                 textAera2.appendText(contact.get(i).printPerson());
            }
        try {
            out = new ObjectOutputStream(new FileOutputStream((plik)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            out1 = new ObjectOutputStream(new FileOutputStream((plik1)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        menu1_1.setDisable(true);
        menu1_2.setDisable(true);
        menu1_3.setDisable(false);
        menu1_4.setDisable(true);
        menu2_1.setDisable(false);
        menu2_2.setDisable(false);
        menu2_3.setDisable(false);
        menu3_1.setDisable(false);
        tab.setVisible(true);
    }
    @FXML
    private void saveFile() throws IOException
    {
        for (int i=0;i<adress.size();i++)
        {
            out1.writeObject(adress.get(i));
        }
        adress = new ArrayList();
        for (int i=0;i<contact.size();i++)
        {
        out.writeObject(contact.get(i));
        }
        contact = new ArrayList();
        out.close();
        out1.close();
        textAera1.setText("");
        textAera2.setText("");
        menu1_1.setDisable(false);
        menu1_2.setDisable(false);
        menu1_3.setDisable(true);
        menu1_4.setDisable(false);
        menu2_1.setDisable(true);
        menu2_2.setDisable(true);
        menu2_3.setDisable(true);
        menu3_1.setDisable(true);
        tab.setVisible(false);
    }
    @FXML
    private void createEntry()
    {
         alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Book");
         alert.setHeaderText("Choose list");
         ButtonType buttonAdress = new ButtonType("Adress");
         ButtonType buttonContact = new ButtonType("Contact");
         alert.getButtonTypes().setAll(buttonAdress,buttonContact);
         ButtonType result = alert.showAndWait().get();
         if (result== buttonContact){
         entry = new TextInputDialog();
         entry.setTitle("Book");
         entry.setHeaderText("Add");
         entry.setContentText("Enter a first name:");
         String firstName = entry.showAndWait().get();
         entry.setHeaderText("Add");
         entry.setContentText("Enter a last name:");
         String lastName = entry.showAndWait().get();
         String email;
            boolean ex = false;
         do{
             entry.setContentText("Enter e-mail adress:");
             email = entry.showAndWait().get();
             int i = email.indexOf('.');
             int a = email.indexOf("@");
             if (i!=-1&&a!=-1) ex=true;
         } while (ex==false);
         String ggNumber;
         boolean flag = false;
         do{
             entry.setContentText("Enter GG Number:");
             ggNumber = entry.showAndWait().get();
             if(Pattern.matches("[a-zA-Z]", ggNumber)){
             flag=true;
         }               
         }while(flag);
         contact.add(new PersonMail(String.valueOf(contact.size()+1),firstName,lastName,email,ggNumber));
         Collections.sort(contact, (PersonMail o1, PersonMail o2) -> o1.compareTo(o2));
         textAera2.setText("");
         for (int i=0;i<contact.size();i++)
         {
             contact.get(i).setIndex(String.valueOf(i));
             textAera2.appendText(contact.get(i).printPerson());
         }
         }
         if (result == buttonAdress ){
            entry = new TextInputDialog();
            entry.setTitle("Book");
            entry.setHeaderText("Add");
            entry.setContentText("Enter a first name:");
            String firstName = entry.showAndWait().get();
            entry.setContentText("Enter a last name:");
            String lastName = entry.showAndWait().get();
            entry.setContentText("Enter a adress:");
            String adress1 = entry.showAndWait().get();
            entry.setContentText("Enter a city");
            String city = entry.showAndWait().get();
            adress.add(new PersonAdress(String.valueOf(adress.size()+1),firstName,lastName,adress1,city));
            Collections.sort(adress, (PersonAdress o1, PersonAdress o2) -> o1.compareTo(o2));
            textAera1.setText("");
            for (int i=0;i<adress.size();i++)
                {
                    adress.get(i).setIndex(String.valueOf(i));
                    textAera1.appendText(adress.get(i).printPerson());
                }
         }
    }
    @FXML
    public void deleteEntry(){
    entry = new TextInputDialog();
        if (contact==null&&adress==null)
        {
            inf = new Alert(Alert.AlertType.ERROR);
            inf.setTitle("Book");
            inf.setHeaderText("Delete");
            inf.setContentText("There is no contacts");
            inf.showAndWait();
        }
      alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Book");
      alert.setHeaderText("Choose list");
      ButtonType buttonAdress = new ButtonType("Adress");
      ButtonType buttonContact = new ButtonType("Contact");
      alert.getButtonTypes().setAll(buttonAdress,buttonContact);
      ButtonType result = alert.showAndWait().get();
      if (result == buttonAdress){
          entry.setTitle("Book");
      entry.setHeaderText("Delete");
      entry.setContentText("Enter a index:");
      String index = entry.showAndWait().get();
      if (Integer.valueOf(index)>=adress.size()){
          alert = new Alert(AlertType.ERROR);
          alert.setContentText("Brak takiego indexu");
          alert.showAndWait();
          return;
      }
      int ind = 0;
      for (int i=0;i<adress.size();i++)
      {
          if (adress.get(i).getIndex().equals(index)){
              adress.remove(i);
              ind=1;
          }
      }
      if (ind==0)
      {
         inf = new Alert(Alert.AlertType.ERROR);
         inf.setTitle("Book");
         inf.setHeaderText("Delete");
         inf.setContentText("There is no contact with " + Integer.toString(ind) + " index");
      }
      textAera1.setText("");
             Collections.sort(adress, PersonAdress::compareTo);

         for (int i=0;i<adress.size();i++)
         {
             adress.get(i).setIndex(String.valueOf(i));
             textAera1.appendText(adress.get(i).printPerson());
         }}
      if (result == buttonContact){
          entry.setTitle("Book");
      entry.setHeaderText("Delete");
      entry.setContentText("Enter a index:");
      String index = entry.showAndWait().get();
      if (Integer.valueOf(index)>=contact.size()){
          alert = new Alert(AlertType.ERROR);
          alert.setContentText("Brak takiego indexu");
          alert.showAndWait();
          return;
      }
          int ind = 0;
      for (int i=0;i<contact.size();i++)
      {
          if (contact.get(i).getIndex().equals(index)){
              contact.remove(i);
              ind=1;
          }
      }
      if (ind==0)
      {
         inf = new Alert(Alert.AlertType.ERROR);
         inf.setTitle("Book");
         inf.setHeaderText("Delete");
         inf.setContentText("There is no contact with " + Integer.toString(ind) + " index");
      }
      textAera2.setText("");
             Collections.sort(contact, PersonMail::compareTo);

         for (int i=0;i<contact.size();i++)
         {
             contact.get(i).setIndex(String.valueOf(i));
             textAera2.appendText(contact.get(i).printPerson());
         }
      }
    }
    @FXML
    public void modifyEntry(){
        entry = new TextInputDialog();
        if (contact==null&&adress==null)
        {
            inf = new Alert(Alert.AlertType.ERROR);
            inf.setTitle("Book");
            inf.setHeaderText("Delete");
            inf.setContentText("There is no contacts");
            inf.showAndWait();
        }
      alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Book");
      alert.setHeaderText("Choose list");
      ButtonType buttonAdress = new ButtonType("Adress");
      ButtonType buttonContact = new ButtonType("Contact");
      alert.getButtonTypes().setAll(buttonAdress,buttonContact);
      ButtonType result = alert.showAndWait().get();
      if (result == buttonContact){
           entry.setTitle("Book");
      entry.setHeaderText("Modify");
      entry.setContentText("Enter a index:");
      String index = entry.showAndWait().get();
      if (Integer.valueOf(index)>=contact.size()){
          alert = new Alert(AlertType.ERROR);
          alert.setContentText("Brak takiego indexu");
          alert.showAndWait();
          return;
      }
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book");
        alert.setHeaderText("Modify");
        alert.setContentText("Which information would you like to modify?");
        ButtonType buttonTypeOne = new ButtonType("First Name");
        ButtonType buttonTypeThree = new ButtonType("Last Name");
        ButtonType buttonTypeFour = new ButtonType("E-Mail");
        ButtonType buttonTypeFive = new ButtonType("GG Number");
        ButtonType buttonTypeTwo = new ButtonType("End");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeThree, buttonTypeFour, buttonTypeFive, buttonTypeTwo);
        boolean loop=false;
        do{
        ButtonType anser = alert.showAndWait().get();
        if (anser==buttonTypeOne){
            entry.setTitle("Book");
            entry.setHeaderText("Edit");
            entry.setContentText("Enter a first name:");
            PersonMail temp = contact.get(Integer.parseInt(index));
            temp.setFirstName(entry.showAndWait().get());
            contact.set(Integer.parseInt(index), temp);
         }
         else if (anser==buttonTypeThree){
            entry.setContentText("Enter a last name");
            PersonMail temp = contact.get(Integer.parseInt(index));
            temp.setLastName(entry.showAndWait().get());
            contact.set(Integer.parseInt(index),temp);
         }
         else if (anser==buttonTypeFour){
             entry.setContentText("Entry e-mail adress:");
             String email;
         Boolean ex=false;
         do{
             entry.setContentText("Entry e-mail adress:");
             email = entry.showAndWait().get();
             int i = email.indexOf('.');
             int a = email.indexOf("@");
             if (i!=-1&&a!=-1) ex=true;
         } while (ex==false);
             PersonMail temp = contact.get(Integer.parseInt(index));
             temp.setMail(email);
             contact.set(Integer.parseInt(index),temp);
         }
         else if (anser==buttonTypeTwo){
             entry.setContentText("Enter GG Number:");
             String gg = entry.showAndWait().get();
             PersonMail temp = contact.get(Integer.parseInt(index));
             temp.setGgNumber(gg);
             contact.set(Integer.parseInt(index), temp);
         }
         else if (anser==buttonTypeFive){
             break;
         }
    }while (loop==true);
      }
      if (result == buttonAdress){
           entry.setTitle("Book");
      entry.setHeaderText("Modify");
      entry.setContentText("Enter a index:");
      String index = entry.showAndWait().get();
      if (Integer.valueOf(index)>=adress.size()){
          alert = new Alert(AlertType.ERROR);
          alert.setContentText("Brak takiego indexu");
          alert.showAndWait();
          return;
      }
          alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book");
        alert.setHeaderText("Modify");
        alert.setContentText("Which information would you like to modify?");
        ButtonType buttonTypeOne = new ButtonType("First Name");
        ButtonType buttonTypeThree = new ButtonType("Last Name");
        ButtonType buttonTypeFour = new ButtonType("Adress");
        ButtonType buttonTypeFive = new ButtonType("City");
        ButtonType buttonTypeTwo = new ButtonType("End");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeThree, buttonTypeFour, buttonTypeFive, buttonTypeTwo);
        boolean loop=false;
        do{
        ButtonType anser = alert.showAndWait().get();
        if (anser==buttonTypeOne){
            entry.setTitle("Book");
            entry.setHeaderText("Edit");
            entry.setContentText("Enter a first name:");
            PersonAdress temp = adress.get(Integer.parseInt(index));
            temp.setFirstName(entry.showAndWait().get());
            adress.set(Integer.parseInt(index), temp);
         }
         else if (anser==buttonTypeThree){
            entry.setContentText("Enter a last name");
            PersonAdress temp = adress.get(Integer.parseInt(index));
            temp.setLastName(entry.showAndWait().get());
            adress.set(Integer.parseInt(index),temp);
         }
         else if (anser==buttonTypeFour){
             entry.setContentText("Entry adress:");
             String adress2 = entry.showAndWait().get();
             PersonAdress temp = adress.get(Integer.parseInt(index));
             temp.setAdress(adress2);
             adress.set(Integer.parseInt(index),temp);
         }
         else if (anser==buttonTypeTwo){
             entry.setContentText("Enter city:");
             String city = entry.showAndWait().get();
             PersonAdress temp = adress.get(Integer.parseInt(index));
             temp.setCity(city);
             adress.set(Integer.parseInt(index), temp);
         }
         else if (anser==buttonTypeFive){
             break;
         }
    }while (loop==true);
      }
        textAera1.setText("");
        textAera2.setText("");
         Collections.sort(adress, (PersonAdress o1, PersonAdress o2) -> o1.compareTo(o2));
         Collections.sort(contact,(PersonMail o1,PersonMail o2) -> o1.compareTo(o2));
         for (int i=0;i<adress.size();i++)
         {
             adress.get(i).setIndex(Integer.toString(i));
             textAera1.appendText(adress.get(i).printPerson());
         }
         for (int i=0;i<contact.size();i++)
         {
             contact.get(i).setIndex(Integer.toString(i));
             textAera2.appendText(contact.get(i).printPerson());
         }
    }
    @FXML
    public void close(){
        System.exit(0);
    }
    @FXML
    public void importSQL(){
        boolean flag=false;
        ResultSet result = null;
        int fl=0;
        do{
        entry = new TextInputDialog();
        entry.setTitle("Book");
        entry.setHeaderText("Database URL");
        entry.setContentText("Entry database url(example jdbc:mysql://localhost:3306/test) :");
        String url = entry.showAndWait().get();
        entry.setHeaderText("Database Login");
        entry.setContentText("Entry database login:");
        String login = entry.showAndWait().get();
        entry.setHeaderText("Database Password");
        entry.setContentText("Entry database password:");
        String password = entry.showAndWait().get();
        database = SingletonSQL.access();        
            fl = database.createConnection(url, login, password);
            if (fl==-1){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("PhoneBook");
                alert.setHeaderText("Connection Error");
                alert.showAndWait();
            }
    }while(fl!=1);
        try {PreparedStatement p = database.conn.prepareStatement("SELECT * FROM book_adress");
            result1 = p.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in query" + ex.getMessage());
        }
         try {
        while(result1.next()){
        adress.add(new PersonAdress(String.valueOf(adress.size()+1),result1.getString(2),result1.getString(3),result1.getString(4),result1.getString(5)));
        }
        } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        flag=true;
        }
        try {
        result1.close();
        } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(adress, (PersonAdress o1, PersonAdress o2) -> o1.compareTo(o2));
        textAera1.setText("");
        for (int i=0;i<adress.size();i++)
        {
        adress.get(i).setIndex(Integer.toString(i));
        textAera1.appendText(adress.get(i).printPerson());
        }
        try {PreparedStatement p = database.conn.prepareStatement("SELECT * FROM book_contact");
            result1 = p.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in query" + ex.getMessage());
        }
         try {
        while(result1.next()){
        contact.add(new PersonMail(String.valueOf(adress.size()+1),result1.getString(2),result1.getString(3),result1.getString(4),result1.getString(5)));
        }
        } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        flag=true;
        }
        try {
        result1.close();
        } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(contact, (PersonMail o1, PersonMail o2) -> o1.compareTo(o2));
        textAera2.setText("");
        for (int i=0;i<contact.size();i++)
        {
        contact.get(i).setIndex(Integer.toString(i));
        textAera2.appendText(contact.get(i).printPerson());
        }
        if (flag==false){
         menu3_1.setDisable(true);
         menu3_2.setDisable(false);}
    }
    @FXML
    private void exportSQL(){
        database.executeUpdate("TRUNCATE book_adress;");
        database.executeUpdate("TRUNCATE book_contact;");
        for (int i=0;i<adress.size();i++){
            PersonAdress temp = adress.get(i);
            database.executeUpdate("INSERT INTO book_adress VALUES('" + temp.getIndex()+"','" + 
                    temp.getFirstName()+"','"+temp.getLastName()+"','"+temp.getAdress()+"','"+temp.getCity() + ");");
        }
        for (int i=0;i<contact.size();i++){
            PersonMail temp = contact.get(i);
            database.executeUpdate("INSERT INTO book_adress VALUES('" + temp.getIndex()+"','" + 
                    temp.getFirstName()+"','"+temp.getLastName()+"','"+temp.getMail()+"','"+temp.getGgNumber() + ");");
        }
        menu3_1.setDisable(false);
        menu3_2.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
