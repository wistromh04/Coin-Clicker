package lab10;
/**
 * Wistrom Herfordt --- Lab 10
 * Last Modified: 4/1
 * 		
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.Scanner;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

/**
 *  Change for a Dollar
 *  NOTE: Must full screen application in order to view individual coin counts 
 */

public class Lab10 extends Application
{
   // Fields for the amount clicked
   private double totalAmount = 0.0;
   private int pennyCount = 0;
   private int nickelCount = 0;
   private int dimeCount = 0;
   private int quarterCount = 0;
   private Label totalLabel;
   
   public static void main(String[] args)
   {
      // Launch the application.
      launch(args);
   }
   
   @Override
   public void start(Stage primaryStage)
   {
   
      // Load the images.
      Image pennyImage = new Image("file:Photos//penny.png");
      Image nickelImage = new Image("file:Photos//nickel.png");
      Image dimeImage = new Image("file:Photos//dime.png");
      Image quarterImage = new Image("file:Photos//quarter.png");
      
      // Create the ImageView controls.
      ImageView pennyIV = new ImageView(pennyImage);
      ImageView nickelIV = new ImageView(nickelImage);
      ImageView dimeIV = new ImageView(dimeImage);
      ImageView quarterIV = new ImageView(quarterImage);
      
      // event handlers for the coins
      pennyIV.setOnMouseClicked(event ->
      {
         totalAmount += 0.01;
         pennyCount++;
         updateTotals();
      });
      
      nickelIV.setOnMouseClicked(event ->
      {
         totalAmount += 0.05;
         nickelCount++;
         updateTotals();
      });
      
      dimeIV.setOnMouseClicked(event ->
      {
         totalAmount += 0.1;
         dimeCount++;
         updateTotals();
      });
      
      quarterIV.setOnMouseClicked(event ->
      {
         totalAmount += 0.25;
         quarterCount++;
         updateTotals();
      });
      
      // Create a title label
     totalLabel = new Label("Total: $0.00");
     totalLabel.setAlignment(Pos.CENTER);
      
     
       // Put the coins in an HBox
      HBox coinHBox = new HBox(10, pennyIV, nickelIV, dimeIV, quarterIV);
      
      //Creates Clear button
      Button clearButton = new Button("Clear");
      clearButton.setOnAction(event -> clearCounts());
      
      //Creates file menu
      Menu fileMenu = new Menu("File");
      MenuItem saveB = new MenuItem("Save");
      MenuItem loadB = new MenuItem("Load");
      
      saveB.setOnAction(event -> saveCounts());
      loadB.setOnAction(event -> loadCounts());
      
      fileMenu.getItems().addAll(saveB, loadB);
      
      MenuItem aboutScreen = new MenuItem("About");
      aboutScreen.setOnAction(event -> showAboutMenu());
      
      //Creates Help Menu
      Menu helpMenu = new Menu("Help");
      helpMenu.getItems().add(aboutScreen);
      
      MenuBar menuBar = new MenuBar(fileMenu, helpMenu);
            
      // Put everything into a VBox
      VBox mainVBox = new VBox(10, menuBar, totalLabel, coinHBox, clearButton);
      mainVBox.setAlignment(Pos.CENTER);
      
      // Add the main VBox to a scene.
      Scene scene = new Scene(mainVBox);
      
      // Set the scene to the stage aand display it.
      primaryStage.setScene(scene);
      primaryStage.show();
      
   // Event handler for the "Count Change" label
      totalLabel.setOnMouseClicked(event ->
      {
          if (totalAmount >= 1.0)
              totalLabel.setText(String.format("Total: $%.2f You Won!", totalAmount));
          else
              totalLabel.setText(String.format("Total: $%.2f You Lose!", totalAmount));
      });
 }
 
   /**
    * This is the method that makes it so when you clock the coins it updates the total label
    */
   private void updateTotals() {
	    totalLabel.setText(String.format("Total: $%.2f\nPennies: %d\nNickels: %d\nDimes: %d\nQuarters: %d",
	            totalAmount, pennyCount, nickelCount, dimeCount, quarterCount));
	}

   
/**
 * clears counts of coins upon click of the clear button
 */
   private void clearCounts() {
       pennyCount = 0;
       nickelCount = 0;
       dimeCount = 0;
       quarterCount = 0;
       totalAmount = 0.0;
       updateTotals();
   }
   
/**
 * save count
 */
   private void saveCounts() {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save Counts to File");
       File file = fileChooser.showSaveDialog(null);

       if (file != null) {
           try (PrintWriter writer = new PrintWriter(file)) {
               writer.println(pennyCount);
               writer.println(nickelCount);
               writer.println(dimeCount);
               writer.println(quarterCount);
           } catch (IOException e) {
        	   System.out.println("Unexpected error found: " + e.getMessage());
           }
       }
   }

   /*
    *load the count
    */
   private void loadCounts() {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Load Counts from File");
       File file = fileChooser.showOpenDialog(null);

       if (file != null) {
           try (Scanner scanner = new Scanner(file)) {
               pennyCount = scanner.nextInt();
               nickelCount = scanner.nextInt();
               dimeCount = scanner.nextInt();
               quarterCount = scanner.nextInt();
               totalAmount = (pennyCount * 0.01) + (nickelCount * 0.05) + (dimeCount * 0.1) + (quarterCount * 0.25);
               updateTotals();
           } catch (FileNotFoundException e) {
        	   System.out.println("Unexpected error found: " + e.getMessage());
           }
       }
   }
  
   /*
    * Functions the about menu which tells you some program info
    */
   private void showAboutMenu() {
	   Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("About");
       alert.setHeaderText("Change for a Dollar\nby: Wistrom Herfordt");
       alert.setContentText("This program lets you click coins and keeps track of the total and individual counts.\nWhen you click the label it will tell you if you are above or below $1.");
       alert.showAndWait();
   }
}