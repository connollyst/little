package com.quane.glass.ide

import java.awt.Point

import scala.swing.event.Event

import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.ExpressionPanelController

/* Drag and drop events are those fired when a user drags an item from the
 * toolkit to the workspace.
 */
sealed trait DragAndDropEvent extends Event

class DropExpressionEvent[T](val name: String,
                             val toolType: ToolType,
                             val point: Point,
                             val dropFunction: () => ExpressionPanelController[Expression[Any]])
        extends DragAndDropEvent

class DragOverEvent[T] protected (val name: String)
    extends DragAndDropEvent

class DragOverWorkspaceEvent(name: String)
    extends DragOverEvent[WorkspacePanel](name)

class DragOutEvent[T] protected (val name: String)
    extends DragAndDropEvent

class DragOutWorkspaceEvent(name: String)
    extends DragOutEvent[WorkspacePanel](name)

/* Menu bar events are those fired from the IDE's top menu bar.
 */
sealed trait MenuBarEvent extends Event

class DoCompileEvent
    extends MenuBarEvent

class DoRunEvent
    extends MenuBarEvent
