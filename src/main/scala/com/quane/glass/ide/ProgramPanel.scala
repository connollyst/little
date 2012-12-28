package com.quane.glass.ide

import java.awt.Dimension
import scala.swing.InternalFrame
import scala.swing.event.MouseEntered
import scala.swing.event.MouseExited
import scala.swing.event.Event
import com.google.common.eventbus.Subscribe
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Component

class ProgramPanel(title: String)
        extends InternalFrame
        with HighlightableComponent {

    super.title = title;
    visible = true
    resizable = true
    closable = true
    maximizable = true
    selected = true
    preferredSize = new Dimension(300, 150)

    val rootPanel = new ProgramRootPanel
    add(rootPanel)

    // Listen for the mouse entering and exiting the panel
    listenTo(mouse.moves)
    reactions += {
        case event: MouseEntered =>
            GlassIDE.eventBus.post(new ProgramEnteredEvent)
        case event: MouseExited =>
            GlassIDE.eventBus.post(new ProgramExitedEvent)
    }

    // Listen for drag & drop events on the event bus
    GlassIDE.eventBus.register(new ProgramDragAndDropEventListener(this))

    override def highlight {
        rootPanel.highlight
    }

    override def unhighlight {
        rootPanel.unhighlight
    }
}

class ProgramRootPanel
        extends BoxPanel(Orientation.Vertical)
        with HighlightableComponent {

    def appendChild(c: Component) {
        contents += c
    }

}

class ProgramEnteredEvent extends Event

class ProgramExitedEvent extends Event

class ProgramDragAndDropEventListener(myPanel: ProgramPanel) {

    var overMe = false;

    @Subscribe
    def dragOverEvent(event: DragOverProgramEvent) {
        overMe = true;
        myPanel.highlight
    }
    @Subscribe
    def dragOutEvent(event: DragOutProgramEvent) {
        overMe = false
        myPanel.unhighlight
    }

    @Subscribe
    def dropEvent(event: DragDropEvent[ProgramPanel]) {
        if (overMe) {
            // TODO compensate for offset in header
            val eventX = event.point.x
            val eventY = event.point.y
            val myX = myPanel.bounds.x
            val myY = myPanel.bounds.y
            val itemX = eventX - myX
            val itemY = eventY - myY
            println("Dropping tool: " + event.name)
            val panel = 
                if ( event.toolType == SetterToolType){
                    new SetterPanel(event.name)
                } else {
                    new ActionPanel(event.name)
                }
            myPanel.rootPanel.appendChild(panel)
            myPanel.unhighlight
            myPanel.pack
        }
    }
}
