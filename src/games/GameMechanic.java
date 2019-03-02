package games;

/**
 * @author Kai Wang
 * A class that contains all the game mechanics
 */
public class GameMechanic{
  
  
  /**
   * Find the location of the element in a 2-D array
   * @param <T> type of the element
   * @param element the element
   * @param array the 2-D array the element is in
   * @return an size-2 int array that stores the row index of the element and the column index of the element
   */
  public static <T> int[] locateElement(T element, T[][] array){
    int[] elementIndex = new int[2];
    
    /**
     * A loop that runs through the array and searches for the element
     * Precondition: The element is in the array
     */
    for(int i = 0; i < array.length ; i ++){
      for(int j = 0; j < array[i].length; j ++){
        if(element.equals(array[i][j])){
          System.out.println("The button is at Row " + (i + 1) + " Column " + (j + 1)); 
          elementIndex[0] = i;
          elementIndex[1] = j;
          return elementIndex;
        }
      }
    }
    return null;
  }
  
  /**
   * Counts the number of same elements to the left side of an element located at a specific index of the array
   * @param <T> type of the element
   * @param rowIndex the row Index of the element
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @return the number of same elements to the left side of the element
   */
   
  public static <T> int countLeft(int rowIndex, int columnIndex, T[][] array){
    int leftCount = 0;  //store the left counts
    
    /**
     * A loop that counts the number of same elements to the left of the element
     * Precondition: There exists a same element to the left of the element currently looking at
     * SubGoal: leftCount stores the amount of same elements to the left of the original element 
     * Goal: leftCount stores the total amount of same elements to the left of the original element
     */
    while(((columnIndex - leftCount) > 0) && (array[rowIndex][columnIndex - leftCount].equals(array[rowIndex][columnIndex - leftCount - 1])))
      leftCount ++;   
    return leftCount;
  }
  
  /**
   * Counts the number of same elements to the right side of an element located at a specific index of the array
   * @param <T> type of the element
   * @param rowIndex the row Index of the element
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @return the number of same elements to the right side of the element
   */
  public static <T> int countRight(int rowIndex, int columnIndex, T[][] array){
    int rightCount = 0;  // store the right counts
    
    /**
     * A loop that counts the number of same elements to the right of the element
     * Precondition: There exists a same element to the right of the element currently looking at
     * SubGoal: rightCount stores the amount of same elements to the right of the original element 
     * Goal: rightCount stores the total amount of same elements to the right of the original element
     */
    while(((columnIndex + rightCount) < (array[rowIndex].length - 1)) && (array[rowIndex][columnIndex + rightCount].equals(array[rowIndex][columnIndex + rightCount + 1])))
      rightCount ++;
    return rightCount;
  }
  
  /**
   * Counts the number of same elements above an element located at a specific index of the array
   * @param <T> type of the element
   * @param rowIndex the row Index of the element
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @return the number of same elements above the element
   */
  public static <T> int countAbove(int rowIndex, int columnIndex, T[][] array){
    int aboveCount = 0; // store the above counts
    
    /**
     * A loop that counts the number of same elements above the element
     * Precondition: There exists a same element above the element currently looking at
     * SubGoal: aboveCount stores the amount of same elements above the original element 
     * Goal: aboveCount stores the total amount of same elements above the original element
     */
    while(((rowIndex - aboveCount) > 0) && (array[rowIndex - aboveCount][columnIndex].equals(array[rowIndex - aboveCount - 1][columnIndex])))
      aboveCount ++;
    return aboveCount;
  }
    
  
  /**
   * Counts the number of same elements below an element located at a specific index of the array
   * @param <T> type of the element
   * @param rowIndex the row Index of the element
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @return the number of same elements below the element
   */
  public static <T> int countBelow(int rowIndex, int columnIndex, T[][] array){
    int belowCount = 0;  // store the below counts
    
    /**
     * A loop that counts the number of same elements below the element
     * Precondition: There exists a same element below the element currently looking at
     * SubGoal: belowCount stores the amount of same elements below the original element 
     * Goal: belowCount stores the total amount of same elements below the original element
     */
    while(((rowIndex + belowCount) < (array.length - 1)) && (array[rowIndex + belowCount][columnIndex].equals(array[rowIndex + belowCount + 1][columnIndex])))
      belowCount ++;
    return belowCount;
  }
  
  /**
   * Remove the element at the specific index of the array, drop the above elements down,
   * and replace the top-most element with the replacement elements
   * @param <T> type of the element
   * @param rowIndex the row Index of the element
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @param replacement the element that will be replacing the top-most element
   */
  public static <T> void removeOneElement(int rowIndex, int columnIndex, T[][] array, T replacement){
    int count = 0; // stores the counts from the element to the boundary of the array 
    
     /**
     * A loop that removes the element and drops the above elements down
     * Precondition: There exists an element above the element currently looking at
     * SubGoal: count stores the number of elements dropped
     * Goal: count stores the total number of elements dropped
     */
    while((rowIndex - count > 0) && (!array[rowIndex - count - 1][columnIndex].equals(replacement))){
      array[rowIndex - count][columnIndex] = array[rowIndex - count - 1][columnIndex];
      count ++;
    }
    
    /* replace the top-most element with the replacement elements */
    array[rowIndex - count][columnIndex] = replacement; 
  }
    
  /**
   * At a specific index of the array, remove all horizontal contiguous same elements except itself, 
   * drop the above elements down, and replace the top-most elements with the replacement elements
   * @param <T> type of the element
   * @param rowIndex the row Index of the element 
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @param replacement the element that will be replacing the top-most elements
   */
  public static <T> void horizontalRemove(int rowIndex, int columnIndex, T[][] array, T replacement){
    
    int leftCount = GameMechanic.countLeft(rowIndex, columnIndex, array);   // stores the left count
    int rightCount = GameMechanic.countRight(rowIndex, columnIndex, array); // stores the right count 
    
    /**
     * A loop that removes all same elements to the left of the element
     * Precondition: Element is to the left of the element
     * Subgoal: All elements to the left of the ith element are removed
     * Goal: All elements to the left of the input element are removed
     */
    for(int i = columnIndex - leftCount; i < columnIndex; i ++){
      removeOneElement(rowIndex, i, array, replacement);
    }
    
    /**
     * A loop that removes all same elements to the right of the element
     * Precondition: Element is to the right of the element
     * Subgoal: All elements to the right of the ith element are removed 
     * Goal: All elements to the right of the input element are removed
     */
    for(int i = columnIndex + rightCount; i > columnIndex; i --){
      removeOneElement(rowIndex, i, array, replacement);
    }
  }
  
  /**
   * At a specific index of the array, remove the element itself and all vertical contiguous same elements, 
   * drop the above elements down, and replace the top-most elements with the replacement elements
   * @param <T> type of the element
   * @param rowIndex the row Index of the element 
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @param replacement the element that will be replacing the top-most elements
   */
  public static <T> void verticalRemove(int rowIndex, int columnIndex, T[][] array, T replacement){
    
    int aboveCount = GameMechanic.countAbove(rowIndex, columnIndex, array);  //stores the above count
    int belowCount = GameMechanic.countBelow(rowIndex, columnIndex, array);  //stores the below count
      
     /**
     * A loop that removes all same elements in the column the element is in
     * Precondition: The element is the same as the input element 
     * Subgoal: i elements have been removed from the column 
     * Goal: All elements that are the same to the input element have been removed from the column
     */
    for(int i = 0; i < (aboveCount + belowCount + 1); i ++)
      removeOneElement(rowIndex + belowCount, columnIndex, array, replacement);
  }
  
  /**
   * shift the right columns to left if there is any empty columns in the array,
   * replace the right-most columns with replacement elements
   * @param <T> type of the element
   * @param array the array looked at
   * @param replacement the element that will be replacing the elements in right-most column
   */
  public static <T> void shiftLeft(T[][] array, T replacement){
    
    int numColumnLeft = array[0].length;  //Stores the number of column left in the array
    
    /**
     * A loop that finds the number of column left in the array
     * PreCondition: There is at least one column, and the column to the left is empty
     * SubGoal: numColumnLeft stores the number of column left in the array
     * Goal: numColumnLeft stores the total number of column left in the array
     */
    while(numColumnLeft != 0 && array[array.length - 1][numColumnLeft - 1].equals(replacement))
      numColumnLeft --;
    
    /**
     * A loop that runs through each column to check if there is an empty column
     * Precondition: The column is one the number of columns left
     */
    for(int j = 0; j < numColumnLeft; j ++){
      if(array[array.length - 1][j].equals(replacement)){  
        
        /**
         * A loop that finds each element in the column
         * Precondition: The element is in the array
         */
        for(int i = 0; i < array.length; i ++){
          int count;
          
          /**
           * A loop that shifts each element to the right of the empty columns to left,
           * and replaces the right-most elements with replacement elements
           * Precondition: The column to the right exist exists
           */
          for(count = 0; j + count < numColumnLeft - 1; count ++){
            array[i][j + count] = array[i][j + count + 1];
          }
          array[i][j + count] = replacement;
        }           
      numColumnLeft -= 1;     //After each shift, reduce the total number of columns left by 1 
      j -= 1;                 //After each shift, re-check the column at the same place to see if the (new)shifed column is also empty 
      }
    }
  }
  
  /**
   * Remove elements from the array according to the rule of the same game
   * @param <T> type of the element
   * @param rowIndex the row Index of the element 
   * @param columnIndex the column Index of the element
   * @param array the array the element is in
   * @param replacement the element that will be replacing the removed elements
   */
  public static <T> void ultimateRemove(int rowIndex, int columnIndex, T[][] array, T replacement){
    
    int aboveCount = GameMechanic.countAbove(rowIndex, columnIndex, array);  //stores the above count
    int belowCount = GameMechanic.countBelow(rowIndex, columnIndex, array);  //stores the below count
    int leftCount = GameMechanic.countLeft(rowIndex, columnIndex, array);    //stores the left count 
    int rightCount = GameMechanic.countRight(rowIndex, columnIndex, array);  //stores the right count
    
    /* 
     * The remove will be exeucted only when the element is allowed to be removed 
     * (Only when there is at least one same element adjacent to the element) 
     */
    if(aboveCount + belowCount + rightCount + leftCount > 0){
      
      /* Remove elements according to the rule of the same game */
      GameMechanic.horizontalRemove(rowIndex, columnIndex, array, replacement);
      GameMechanic.verticalRemove(rowIndex, columnIndex, array, replacement);
      GameMechanic.shiftLeft(array, replacement); 
    }
  }
}