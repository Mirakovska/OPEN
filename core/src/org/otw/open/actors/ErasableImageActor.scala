package org.otw.open.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap.Blending
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.{Pixmap, Texture}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener}
import com.badlogic.gdx.utils.Disposable
import org.otw.open.controllers.{EraserGameFinished, ScreenController}
import org.otw.open.dto.DrawablePixmap

/**
  * Created by eilievska on 2/12/2016.
  */
class ErasableImageActor extends Actor with Disposable {

  private val pixmapMask: DrawablePixmap = new DrawablePixmap(new Pixmap(Gdx.files.internal("theme/car_theme/mask.png")))
  private val maskTexture: Texture = pixmapMask.initializePixmapDrawingOntoTexture

  Pixmap.setBlending(Blending.None)

  private var lastPointerPosition: Option[Vector2] = None
  private var currentPointerPosition: Option[Vector2] = None

  setX(0)
  setY(0)
  setWidth(maskTexture.getWidth)
  setHeight(maskTexture.getHeight)
  setBounds(0, 0, maskTexture.getWidth, maskTexture.getHeight)

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    val mouseWasMoved: Boolean = lastPointerPosition.isDefined && lastPointerPosition.get != currentPointerPosition.get
    if (mouseWasMoved) {
      pixmapMask.drawLerped(lastPointerPosition.get, currentPointerPosition.get)
      pixmapMask.drawOnTexture(maskTexture)
      if (pixmapMask.isTransparent) ScreenController.dispatchEvent(EraserGameFinished)
    }
    lastPointerPosition = currentPointerPosition
    batch.draw(maskTexture, 0, 0)
  }

  addListener(new InputListener() {
    override def mouseMoved(event: InputEvent, x: Float, y: Float) = {
      currentPointerPosition = Some(new Vector2(x, 900 - y))
      true
    }
  })

  override def dispose(): Unit = {
    pixmapMask.dispose
    maskTexture.dispose
  }
}