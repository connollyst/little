package com.quane.little.ide.dnd

import scala.swing.Component
import scala.swing.event.MouseEntered
import com.quane.little.ide.IDE
import scala.swing.event.MouseExited
import com.quane.little.ide.DragOverEvent
import com.quane.little.ide.swing.HighlightableComponent
import com.quane.little.ide.DragOutEvent
import com.quane.little.ide.DropExpressionEvent

trait DragAndDropTarget
        extends HighlightableComponent {

    /** Returns true/false if the specified item can/cannot be dropped here,
      * respectively.
      *
      * @param item
      * 		the drag and drop item
      */
    def accepts(item: DragAndDropItem): Boolean;

    def onDrop(event: DropExpressionEvent): Unit;

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
            onDrop(event)
    }
}