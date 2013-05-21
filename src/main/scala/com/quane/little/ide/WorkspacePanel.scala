package com.quane.little.ide

import java.awt.Color
import java.awt.Point
import scala.collection.mutable.ListBuffer
import scala.swing.DesktopPanel
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import org.eintr.loglady.Logging
import com.quane.little.language.event.EventListener
import com.quane.little.language.Expression
import com.quane.little.ide.language.ExpressionController
import com.quane.little.ide.language.WorkspaceFrame
import com.quane.little.ide.language.WorkspaceFrameController
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.language.FunctionController
import com.quane.little.ide.language.ListenerController
import com.quane.little.ide.dnd.{EventToolType, DragAndDropItem, DragAndDropTarget}

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with DragAndDropTarget
        with Logging {

    background = Color.white

    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case EventToolType => true
            case _             => false
        }
    }

    def onDrop(event: DropExpressionEvent): Unit = {
        val controller = event.dropFunction()
        controller match {
            case listenerController: ListenerController =>
                log.info("Accepting a " + event.toolType.getClass().getSimpleName())
                val point = relativeCoordinates(event.point)
                publish(
                    new ListenerAddedEvent(
                        listenerController,
                        event.toolType,
                        point.x,
                        point.y
                    )
                )
            case functionController: FunctionController =>
                log.info("Accepting a " + event.toolType.getClass().getSimpleName())
                val point = relativeCoordinates(event.point)
                publish(
                    new FunctionAddedEvent(
                        functionController,
                        event.toolType,
                        point.x,
                        point.y
                    )
                )
            case _ =>
                log.error(
                    classOf[WorkspacePanel].getSimpleName()
                        + " can't accept drop of "
                        + controller.getClass().getSimpleName()
                )
        }
    }

    /** Adds a [[WorkspaceFrame]] to the workspace.
      *
      * @param frame
      * 		the [[WorkspaceFrame]] to be added to the [[WorkspacePanel]]
      */
    def add(frame: WorkspaceFrame) {
        super.add(frame);
    }

    /** Translates an absolute [[Point]] to a relative point. That is, the
      * x and y coordinates of the point are adjusted to be relative to the
      * [[WorkspacePanel]].
      *
      * @param point
      * 		the [[Point]] with absolute coordinates
      * @return
      * 		a [[Point]] with relative coordinates
      */
    def relativeCoordinates(point: Point): Point = {
        val eventX = point.x
        val eventY = point.y
        // TODO compensate for offset in header
        val workspaceX = bounds.x
        val workspaceY = bounds.y
        val relativeX = eventX - workspaceX
        val relativeY = eventY - workspaceY
        new Point(relativeX, relativeY)
    }
}
