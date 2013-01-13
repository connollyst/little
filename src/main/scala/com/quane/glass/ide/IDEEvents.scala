package com.quane.glass.ide

import java.awt.Point
import scala.swing.event.Event
import com.quane.glass.ide.language.GlassPanel
import com.quane.glass.ide.language.GlassEventFrame
import com.quane.glass.ide.language.GlassPanelController
import com.quane.glass.core.language.Expression

/* Drag and drop events are those fired when a user drags an item from the
 * toolkit to the workspace.
 */
sealed trait DragAndDropEvent extends Event

class DragDropEvent[T](val name: String,
                       val toolType: ToolType,
                       val point: Point,
                       val controllerFactoryFunction: () => GlassPanelController[Expression[Any]])
        extends DragAndDropEvent

class DragOverEvent[T] protected (val name: String)
    extends DragAndDropEvent

class DragOutEvent[T] protected (val name: String)
    extends DragAndDropEvent

class DragOverWorkspaceEvent(name: String)
    extends DragOverEvent[WorkspacePanel](name)

class DragOutWorkspaceEvent(name: String)
    extends DragOutEvent[WorkspacePanel](name)

class DragOverProgramEvent(name: String)
    extends DragOverEvent[GlassEventFrame](name)

class DragOutProgramEvent(name: String)
    extends DragOutEvent[GlassEventFrame](name)

/* Menu bar events are those fired from the IDE's top menu bar.
 */
sealed trait MenuBarEvent extends Event

class DoCompileEvent
    extends MenuBarEvent

class DoRunEvent
    extends MenuBarEvent