package com.quane.little.ide.dnd

import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import com.google.common.eventbus.Subscribe
import org.eintr.loglady.Logging
import com.google.common.eventbus.Subscribe
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.DropExpressionEvent
import com.quane.little.ide.ToolDraggedEvent
import com.quane.little.ide.ToolDroppedEvent
import com.quane.little.ide.ToolType

class DragAndDropEventListener
        extends Logging {

    var toolType = None: Option[ToolType]
    var target = None: Option[DragAndDropTarget]

    @Subscribe
    def dragEvent(event: ToolDraggedEvent) {
        toolType = Option(event.toolType)
    }

    @Subscribe
    def dropEvent(event: ToolDroppedEvent) {
        log.info("ToolDroppedEvent")
        if (toolType.isDefined && target.isDefined) {
            target.get.publish(
                new DropExpressionEvent(target.get, toolType.get, event.point, event.dropFunction)
            )
        }
        toolType = None
        target = None
    }

    @Subscribe
    def overEvent(event: MouseEntered) {
        if (toolType.isDefined) {
            event.source match {
                case dndTarget: DragAndDropTarget =>
                    if (dndTarget.accepts(toolType.get)) {
                        target = Option(dndTarget)
                        target.get.publish(
                            new DragOverEvent(target.get, toolType.get, event.point)
                        )
                    } else {
                        val targetClass = event.source.getClass().getSimpleName
                        val toolTypeClass = toolType.get.getClass().getSimpleName
                        log.debug(targetClass + " doesnt accept " + toolTypeClass)
                    }
                case _ =>
                    log.error("A tool was dragged over a " + event.source.getClass().getSimpleName()
                        + ", who is listening, but isn't a " + classOf[DragAndDropTarget].getSimpleName())
            }
        }
    }

    @Subscribe
    def outEvent(event: MouseExited) {
        if (toolType.isDefined) {
            if (target.isDefined) {
                target.get.publish(
                    new DragOutEvent(target.get, toolType.get, event.point)
                )
            }
            target = None
        }
    }

}