﻿//USEUNIT CauseEffect_01_ContinuosMovement
//USEUNIT CauseEffect_03_OneClick
//USEUNIT CauseEffect_04_ClicksOutofBounds
//USEUNIT CauseEffect_05_MultipleClicks
//USEUNIT CauseEffect_19_GameMenu
//USEUNIT CauseEffect_14_Drag_and_Drop
//USEUNIT CauseEffect_15_DragAndDropFailed

function causeEffect_OneClick()
{
try{
  //Launch the tested application.
  launchApp();
  
  //Removing leaves from the top layer of the scene by continuous movement of the mouse
  Log.AppendFolder("This test is for ContinuousMovement of the mouse");
  eraser ();
 //Navigation towards next level 
  navigationNextLevel();
  Log.PopLogFolder(); 
  
  Log.AppendFolder("This test for failed attempt on   one click level");  
  multipleClicksOutOfBounds();  
   
  Log.PopLogFolder();
  restart()
 Log.AppendFolder("This test is for Retry  of one click level");  
 oneClickCar ();      
 
  //Navigation towards next level 
  navigationNextLevel();
  Log.PopLogFolder(); 
  
  //Click the object on the screen so that it reaches the target in one step
  Log.AppendFolder("This test for failed attempt on 3 clicks level");  
  multipleClicksOutOfBounds();
  
  Log.PopLogFolder();
  restart() 
  Log.AppendFolder("This test is for 3 clicks level");
  //Navigation towards next level 
  aqUtils.Delay(1000);
  multipleClicks();
  
  navigationNextLevel();
  Log.PopLogFolder();
   
  
  //Click the object on the screen so that it moves forward 1/3 towards the target and reach the target in 3 steps
  Log.AppendFolder("This test for failed attempt on drag and drop level");  
  DragAndDropFailed();  
  Log.PopLogFolder(); 
  restart()
Log.AppendFolder("This test is for Reaching the goal with drag and drop");  
  DragAndDrop();  
  Log.PopLogFolder();
  
  
 }
 catch(e)
  {
    // Posts an exception message to the test log
    Log.Error(e.description);
    // Stops the test execution
    Runner.Stop();
  }
 finally
 {     
   closeApp();
 }
}