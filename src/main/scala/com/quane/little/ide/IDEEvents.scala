package com.quane.little.ide

import java.awt.Point
import scala.swing.event.Event
import com.quane.little.language.Expression
import com.quane.little.ide.language.ExpressionController
import scala.swing.Component

/* Drag and drop events are those fired when a user drags an item from the
 * toolkit to the workspace.
 */
abstract class DragAndDropEvent(val source: Component,
                                val toolType: ToolType,
                                val point: Point)
        extends Event

class MouseEnteredEvent(override val source: Component,
                        override val toolType: ToolType,
                        override val point: Point)
        extends DragAndDropEvent(source, toolType, point)

class MouseExitedEvent(override val source: Component,
                       override val toolType: ToolType,
                       override val point: Point)
        extends DragAndDropEvent(source, toolType, point)

class DragOverEvent(override val source: Component,
                    override val toolType: ToolType,
                    override val point: Point)
        extends DragAndDropEvent(source, toolType, point)

class DragOutEvent(override val source: Component,
                   override val toolType: ToolType,
                   override val point: Point)
        extends DragAndDropEvent(source, toolType, point)

class DropExpressionEvent(override val source: Component,
                          override val toolType: ToolType,
                          override val point: Point,
                          val dropFunction: () => ExpressionController[Expression[Any]])
        extends DragAndDropEvent(source, toolType, point)

/* Menu bar events are those fired from the IDE's top menu bar.
 */
sealed trait MenuBarEvent extends Event

class DoCompileEvent
    extends MenuBarEvent

class DoRunEvent
    extends MenuBarEvent
