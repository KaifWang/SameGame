package games;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.shape.Shape;
import java.util.Timer; //For extra Credit
import java.util.TimerTask;
import javax.swing.JOptionPane;
  
/** A class that represents Same Game
  * @author Kai Wang
  */
public class SameGame extends Application{
  
  /* store the number of rows */
  private int numRow = 12;
  
  /* store the number of columns */
  private int numColumn = 12;
  
  /* store the number of colors */
  private int numColor = 3;
  
  /* store the 2-dimensional array of colors */
  private Color[][] colors;
  
  /* store the 2-dimensional array of buttons*/
  private Button[][] buttons;
  
  /* store the avaiable colors(max 7) */
  private final Color[] colorArray = {Color.CHARTREUSE, Color.DARKORCHID, Color.DEEPSKYBLUE, Color.RED, Color.YELLOW, Color.ORANGE, Color.MAROON};
    
  /**
   * change the number of rows
   * @param numRow number of rows
   */
  public void setNumRow(int numRow){
    this.numRow = numRow;
  }
    
  /**
   * change the number of columns
   * @param numColumn number of columns
   */
  public void setNumColumn(int numColumn){
    this.numColumn = numColumn;
  }
  
  /**
   * change the number of colors
   * @param numColor the number of colors
   */
  public void setNumColor(int numColor){
    this.numColor = numColor;
  }
  
      
  /**
   * change the 2-dimensional array of colors
   * @param colors 2-dimensional array of colors
   */
  public void setColors(Color[][] colors){
    this.colors = colors;
  }
  
   /**
   * change the 2-dimensional array of buttons
   * @param buttons 2-dimensional array of buttons
   */
  public void setButtons(Button[][] buttons){
    this.buttons = buttons;
  }
  
  /**
   * get the number of rows
   * @return the number of rows
   */
  public int getNumRow(){
    return numRow;
  }
  
  /**
   * get the number of columns
   * @return the number of columns
   */
  public int getNumColumn(){
    return numColumn;
  }
  
  /**
   * get the number of colors
   * @return the number of colors
   */
  public int getNumColor(){
    return numColor;
  }
  
  /**
   * get the 2-dimensional array of colors
   * @return the 2-dimensional array of colors
   */
  public Color[][] getColors(){
    return colors;
  }
  
  /**
   * get the 2-dimensional array of buttons
   * @return the 2-dimensional array of buttons
   */
  public Button[][] getButtons(){
    return buttons;
  }
  
  
  /** 
   * Fill the array of buttons with the array of colors
   * @param colors the 2-dimensional array of colors
   * @param buttons the 2-dimensional array of buttons
   */
  public void fill(Color[][] colors, Button[][] buttons){
     for(int i = 0; i < getNumRow() ; i ++){
       for(int j = 0; j < getNumColumn(); j ++){
         ((Circle)buttons[i][j].getGraphic()).setFill(colors[i][j]);
       }
     }
  }
  
  
  /**
   * Initialize the Samegame with random colors on (number of Rows) X (number of Columns) buttons
   * @param primaryStage the JavaFX main window
   */
  public void start(Stage primaryStage){
    GridPane gridpane = new GridPane();                   // Creates and stores a gridpane that holds the buttons
    Scene scene = new Scene(gridpane);                    // Creates and stores a scene that holds the grid pane
    primaryStage.setScene(scene);
    
    /* Initialize the parameters of the game based on given command line input */
    if(getParameters().getRaw().size() == 3){
      setNumRow(Integer.parseInt(getParameters().getRaw().get(0)));
      setNumColumn(Integer.parseInt(getParameters().getRaw().get(1)));
      setNumColor(Integer.parseInt(getParameters().getRaw().get(2)));
    }
    setButtons(new Button[getNumRow()][getNumColumn()]);  // sets the size of the 2-dimensional array of buttons based on number of columns and rows
    setColors(new Color[getNumRow()][getNumColumn()]);    // sets the size of the 2-dimensional array of buttons based on number of columns and rows
    
    /**
     * A loop that runs through the array of buttons to initialize the buttons
     * and runs through the array of colors to initialize the colors with random colors
     * Precondition: the index stays in the size of the arrays
     */
    for(int i = 0; i < getNumRow() ; i ++){
      for(int j = 0; j < getNumColumn(); j ++){
        buttons[i][j] = new Button();                    // creates a button
        Circle c = new Circle(13);                       // creates a circle
        buttons[i][j].setGraphic(c);                     // puts the circle on the button
        gridpane.add(buttons[i][j], j, i);               // puts the button on the gridpane with corresponding index
        buttons[i][j].setOnAction(new ClickHandler());   // sets the button on action(responding to click)
        
        /* initialize the array of colors with random colors selected from the avaiable colors */
        colors[i][j] = (colorArray[(int)(Math.random() * getNumColor())]); 
      }
    }
    fill(colors, buttons);                               // paint the array of buttons with the array of colors
    primaryStage.show();                                 // show the main window
  }
  

  /**
   * A nested class that handles button click by the rule of the game
   */
  private class ClickHandler implements EventHandler<ActionEvent>{
    
    /* stores the row index of the button clicked */
    private int rowIndex;                    
    
    /* stores the column index of the button clicked */
    private int columnIndex;   
        
    /**
     * handle the button click by the rule of the game
     * @param e the action event of the click
     */
    public void handle(ActionEvent e){
       Button b = (Button)e.getSource();                                    // store the button that is clicked
       int[] buttonIndex = GameMechanic.locateElement(b, buttons);          // Find the location of the clicked button in the buttons array
       rowIndex = buttonIndex[0];                                           // store the row index of the clicked button
       columnIndex = buttonIndex[1];                                        // store the column index of the clicked button
       
       /** Remove colors(buttons) according to the rule of same game */
       GameMechanic.ultimateRemove(rowIndex, columnIndex, colors, Color.LIGHTGREY);
       
       /* fill(update) the array of buttons with the changed array of colors */
       fill(colors, buttons);
       
       
       
       
       /** Extra Credit */
       /** Informs user the winning of the game */
       if(colors[getNumRow() -1][0] == Color.LIGHTGREY){
         Timer t = new Timer();
         t.schedule(new TimerTask(){
           public void run(){
             JOptionPane.showMessageDialog(null, "怎么那么聪明^_^鼓励鼓励");
           }
         }, 50);              
       }
       
       /** Informs user the lost of the game */
       int numLeft = 0;
       int numLeftUnclickable = 0;

       for(int i = 0; i < getNumRow() ; i ++){
         for(int j = 0; j < getNumColumn(); j ++){
           if(colors[i][j] != Color.LIGHTGREY){
             numLeft += 1;
             int aboveCount = GameMechanic.countAbove(i, j, colors);  //stores the above count
             int belowCount = GameMechanic.countBelow(i, j, colors);  //stores the below count
             int leftCount = GameMechanic.countLeft(i, j, colors);    //stores the left count 
             int rightCount = GameMechanic.countRight(i, j, colors);  //stores the right count
             if(aboveCount + belowCount + rightCount + leftCount == 0){
               numLeftUnclickable += 1;
             }
           }
         }
       }
       if(numLeftUnclickable == numLeft){
         Timer t = new Timer();
         t.schedule(new TimerTask(){
           public void run(){
             JOptionPane.showMessageDialog(null, "消不完啦傻子哈哈哈哈哈哈哈");
           }
         }, 50);
       }
       /**Extra Credit Ends Here */
       
    }
  }
  
  
  
  /**
   * Main method that starts the application
   * @param args the command line argument
   */
  public static void main(String[] args){
    
    /* launch the application with no input(default parameters: 12 x 12 board with 3 colors) */
    if(args.length == 0) 
      Application.launch(args);
    
    /* accept 3 inputs from user: number of rows, number of columns, number of colors */
    else if (args.length == 3){
      boolean isInteger = true;                              // a boolean that checks if all inputs are integers
      try{
        Integer.parseInt(args[0]);
        Integer.parseInt(args[1]);
        Integer.parseInt(args[2]);
      }
      catch(NumberFormatException e){
        System.out.println("Enter Integers Only Please");                            // informs the user if not all inputs are integers
        isInteger = false;
      }
      
      if(isInteger){
        if(Integer.parseInt(args[2]) > 7)                 
          System.out.println("Only 7 colors available, please try");                 // informs the user if the number of colors input exceeds the maximum
        else if(Integer.parseInt(args[0]) > 20 || Integer.parseInt(args[1]) > 30)
           System.out.println("Maxium size of the board is 20 X 30, please try");    // informs the user if the input dimension for the board is too big
        else
          Application.launch(args);                          
      }
    }
    else
    System.out.println("Please give 3 inputs or no inputs"); // informs the user if the number of inputs is neither 3 nor 0
  }
}