package com.quane.glass.ide

import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import com.google.common.eventbus.Subscribe
import scala.swing.Component
import org.eintr.loglady.Logging

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
                        log.debug("doesnt accept tool")
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