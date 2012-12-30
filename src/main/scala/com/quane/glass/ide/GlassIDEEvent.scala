package com.quane.glass.ide

import java.awt.Point
import scala.swing.event.Event
import com.quane.glass.ide.language.GlassPanel
import com.quane.glass.ide.language.GlassEventFrame
import com.quane.glass.ide.language.GlassPanelController
import com.quane.glass.core.language.Expression

trait DragAndDropEvent extends Event

class DragOverEvent[T](val name: String)
    extends DragAndDropEvent

class DragOutEvent[T](val name: String)
    extends DragAndDropEvent

class DragDropEvent[T](val name: String, val toolType: ToolType, val point: Point, val controller: GlassPanelController[Expression[Any]])
    extends DragAndDropEvent

class DragOverWorkspaceEvent(name: String)
    extends DragOverEvent[WorkspacePanel](name)

class DragOutWorkspaceEvent(name: String)
    extends DragOutEvent[WorkspacePanel](name)

class DragDropWorkspaceEvent(name: String, toolType: ToolType, point: Point, controller: GlassPanelController[Expression[Any]])
    extends DragDropEvent[WorkspacePanel](name, toolType, point, controller)

class DragOverProgramEvent(name: String)
    extends DragOverEvent[GlassEventFrame](name)

class DragOutProgramEvent(name: String)
    extends DragOutEvent[GlassEventFrame](name)

class DragDropProgramEvent(name: String, toolType: ToolType, point: Point, controller: GlassPanelController[Expression[Any]])
    extends DragDropEvent[GlassEventFrame](name, toolType, point, controller)