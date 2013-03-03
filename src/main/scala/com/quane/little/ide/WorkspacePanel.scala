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
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.ide.language.WorkspaceFrame
import com.quane.little.ide.language.WorkspaceFrameController
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.language.FunctionPanelController
import com.quane.little.ide.language.ListenerPanelController
import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.dnd.DragAndDropTarget

/** The workspace is the area in which programs are created and edited.
  *
  * @author Sean Connolly
  */
class WorkspacePanel
        extends DesktopPanel
        with DragAndDropTarget
        with HighlightableComponent
        with Logging {

    background = Color.white

    /** Returns true/false if the specified item can/cannot be dropped here,
      * respectively.
      *
      * @param item
      * 		the drag and drop item
      */
    def accepts(item: DragAndDropItem): Boolean = {
        return item match {
            case EventToolType => true
            case _             => false
        }
    }

    /** Adds a {@link WorkspaceFrame} to the workspace.
      *
      * @param frame
      * 		the {@link WorkspaceFrame} to be added to the
      * 		{@link WorkspacePanel}
      */
    def add(frame: WorkspaceFrame) {
        super.add(frame);
    }

    // Listen for the mouse entering and exiting the workspace
    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            IDE.eventBus.post(event)
        case event: MouseExited =>
            IDE.eventBus.post(event)
        case event: DragOverEvent =>
            highlight
        case event: DragOutEvent =>
            unhighlight
        case event: DropExpressionEvent =>
            unhighlight
            val controller = event.dropFunction()
            controller match {
                case listenerController: ListenerPanelController =>
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
                case functionController: FunctionPanelController =>
                    log.info("Accepting a "+event.toolType.getClass().getSimpleName())
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

    /** Translates an absolute {@link Point} to a relative point. That is, the
      * x and y coordinates of the point are adjusted to be relative to the
      * {@link WorkspacePanel}.
      *
      * @param point
      * 		the {@link Point} with absolute coordinates
      * @return
      * 		a {@link Point} with relative coordinates
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
