package com.quane.glass.ide

import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import com.google.common.eventbus.Subscribe
import scala.swing.Component

class DragAndDropEventListener {

    var toolType = None: Option[ToolType]
    var target = None: Option[Component]

    @Subscribe
    def dragEvent(event: ToolDraggedEvent) {
        toolType = Option(event.toolType)
    }

    @Subscribe
    def dropEvent(event: ToolDroppedEvent) {
        println("ToolDroppedEvent")
        if (toolType.isDefined && target.isDefined) {
            target.get.publish(
                new DropExpressionEvent(target.get, toolType.get, event.point, event.controllerFactoryFunction)
            )
            toolType = None
            target = None
        }
    }

    @Subscribe
    def overEvent(event: MouseEntered) {
        if (toolType.isDefined) {
            target = Option(event.source)
            event.source.publish(
                new DragOverEvent(event.source, toolType.get, event.point)
            )
        }
    }

    @Subscribe
    def outEvent(event: MouseExited) {
        if (toolType.isDefined) {
            event.source.publish(
                new DragOutEvent(event.source, toolType.get, event.point)
            )
            target = None
        }
    }

}