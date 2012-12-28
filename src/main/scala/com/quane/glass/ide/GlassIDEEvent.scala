package com.quane.glass.ide

import scala.swing.event.Event
import java.awt.Point

trait DragAndDropEvent extends Event

class DragOverEvent[T](val name: String)
    extends DragAndDropEvent

class DragOutEvent[T](val name: String)
    extends DragAndDropEvent

class DragDropEvent[T](val name: String, val toolType: ToolType, val point: Point)
    extends DragAndDropEvent

class DragOverWorkspaceEvent(name: String)
    extends DragOverEvent[WorkspacePanel](name)

class DragOutWorkspaceEvent(name: String)
    extends DragOutEvent[WorkspacePanel](name)

class DragDropWorkspaceEvent(name: String, toolType: ToolType, point: Point)
    extends DragDropEvent[WorkspacePanel](name, toolType, point)

class DragOverProgramEvent(name: String)
    extends DragOverEvent[ProgramPanel](name)

class DragOutProgramEvent(name: String)
    extends DragOutEvent[ProgramPanel](name)

class DragDropProgramEvent(name: String, toolType: ToolType, point: Point)
    extends DragDropEvent[ProgramPanel](name, toolType, point)