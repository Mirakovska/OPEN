package org.otw.open.controllers

import com.badlogic.gdx.Screen
import org.otw.open.screens._
import org.otw.open.testconfig.UnitSpec
import org.otw.open.OpenGame
import org.otw.open.util.UserSettings

/**
  * Created by eilievska on 1/28/2016.
  */
class ScreenControllerTest extends UnitSpec {

  test("given EraserGameFinished event is dispatched, the returned game screen is the Successful Action screen.") {
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(EraserGameFinished)
    val actionResultScreen: ActionResultScreen = castActionResultScreen(returnedScreen)
    assert(actionResultScreen.isSuccessfulAction)
    assert(returnedScreen.isInstanceOf[ActionResultScreen])
  }

  def castActionResultScreen(returnedScreen: AbstractGameScreen): ActionResultScreen = {
    val actionResultScreen: ActionResultScreen = returnedScreen match {
      case screen: ActionResultScreen => screen
      case _ => throw new scala.ClassCastException
    }
    actionResultScreen
  }

  test("given EraserGameFinished event is dispatched,the current game screen is the Successful Action screen.") {
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(EraserGameFinished)
    val currentGameScreen = getCurrentGameScreen
    assert(currentGameScreen == returnedScreen)
  }

  test("given CauseAndEffectFinishedSuccessfully event is dispatched, the returned game screen is the Successful Action screen.") {
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(CauseAndEffectFinishedSuccessfully)
    val actionResultScreen: ActionResultScreen = castActionResultScreen(returnedScreen)
    assert(returnedScreen.isInstanceOf[ActionResultScreen])
    assert(actionResultScreen.isSuccessfulAction)
  }

  test("given CauseAndEffectFinishedSuccessfully event is dispatched, the current game screen is the Successful Action screen.") {
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(CauseAndEffectFinishedSuccessfully)
    val currentGameScreen = getCurrentGameScreen
    assert(returnedScreen == currentGameScreen)
  }

  test("given CauseAndEffectFinishedUnsuccessfully event is dispatched, the returned game screen engine is the Unsuccessful Action screen.") {
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(CauseAndEffectFinishedUnsuccessfully)
    val actionResultScreen: ActionResultScreen = castActionResultScreen(returnedScreen)
    assert(returnedScreen.isInstanceOf[ActionResultScreen])
    assert(!actionResultScreen.isSuccessfulAction)
  }

  test("given CauseAndEffectFinishedUnsuccessfully event is dispatched, the current game screen engine is the Unsuccessful Action screen.") {
    val returnedScreenEngine: AbstractGameScreen = ScreenController.dispatchEvent(CauseAndEffectFinishedUnsuccessfully)
    val currentGameScreen = getCurrentGameScreen
    assert(returnedScreenEngine == currentGameScreen)
  }

  test("given RetryLevel event is dispatched, the current game screen engine is CauseAndEffectEngine") {
    val previousLevel = GameState.getLevel
    ScreenController.dispatchEvent(RetryLevel)
    assert(previousLevel == GameState.getLevel)
  }

  test("given NextLevel event is dispatched, the current game screen engine is EraserGameEngine") {
    val currentLevel = GameState.getLevel
    ScreenController.dispatchEvent(NextLevel)
    assert(GameState.getLevel != currentLevel)
  }

  test("given OtherTheme event is dispatched, the current game screen engine is EraserGameEngine") {
    val returnedScreenEngine: AbstractGameScreen = ScreenController.dispatchEvent(OtherTheme)
    val currentGameScreen = getCurrentGameScreen
    assert(returnedScreenEngine == currentGameScreen)

  }

  test("given ToMainMenu event is dispatched, the current game screen engine is MainMenuScreen") {
    ScreenController.dispatchEvent(NextLevel)
    val returnedScreenEngine: AbstractGameScreen = ScreenController.dispatchEvent(ToMainMenu)
    assert(returnedScreenEngine.isInstanceOf[MainMenuScreen])
    assert(GameState.getLevel == 1)
  }

  test("given the current level is 4, the getScreenByLevel should return CauseAndEffect screen") {
    GameState.setLevel(4)
    val returnedScreenEngine: AbstractGameScreen = ScreenController.getScreenByLevel
    assert(returnedScreenEngine.isInstanceOf[CauseAndEffectScreen])
  }

  test("given the current level is 3, the getScreenByLevel should return CauseAndEffect screen") {
    GameState.setLevel(3)
    val returnedScreenEngine: AbstractGameScreen = ScreenController.getScreenByLevel
    assert(returnedScreenEngine.isInstanceOf[CauseAndEffectScreen])
  }

  test("given ToTheme event is dispatched, the returned game screen is the next theme but same level.") {
    GameState.setLevel(1)
    val returnedScreen: AbstractGameScreen = ScreenController.dispatchEvent(ToTheme)
    assert(returnedScreen.isInstanceOf[EraserGameScreen])
  }


  test("given pointer is set to small, getPointerSizeFromUserSettings should return 64") {
    UserSettings.pointerSize = "s"
    assert(ScreenController.getPointerSizeFromUserSettings == 64)
  }

  test("given pointer is set to medium, getPointerSizeFromUserSettings should return 128") {
    UserSettings.pointerSize = "m"
    assert(ScreenController.getPointerSizeFromUserSettings == 128)
  }

  /**
    *
    * @return gets the current screen of our singleton game instance.
    */
  private def getCurrentGameScreen: Screen = OpenGame.getGame.getScreen

}
