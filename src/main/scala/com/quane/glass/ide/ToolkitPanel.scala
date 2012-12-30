package com.quane.glass.ide

import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Component
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.Reactor
import scala.swing.event.Event
import scala.swing.event.MouseEvent
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import com.google.common.eventbus.EventBus
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.AssignmentStatementPanel
import com.quane.glass.ide.language.AssignmentStatementPanelController
import com.quane.glass.ide.language.GlassPanel
import com.quane.glass.ide.language.GlassPanelController
import com.quane.glass.ide.language.PrintStatementPanel
import javax.swing.BorderFactory
import com.quane.glass.ide.language.PrintStatementPanelController

class ToolkitPanel
        extends BoxPanel(Orientation.Vertical) {

    border = BorderFactory.createLineBorder(Color.black)
    preferredSize = new Dimension(200, 600);
    minimumSize = preferredSize;

    val eventsToolPanel = new EventsToolPanel
    val settersToolPanel = new SettersToolPanel

    contents += eventsToolPanel
    contents += settersToolPanel

}

class ToolPanel(title: String, val toolType: ToolType)
        extends BoxPanel(Orientation.Vertical) {

    val titleLabel = new Label(title)
    val toolList = new BoxPanel(Orientation.Vertical)

    contents += titleLabel
    contents += toolList

}

class EventsToolPanel
        extends ToolPanel("Events", EventToolType) {

    toolList.contents += new ToolButton("OnSpawn", toolType, null)
    toolList.contents += new ToolButton("OnContact", toolType, null)
    toolList.contents += new ToolButton("OnContactEnded", toolType, null)
    toolList.contents += new ToolButton("WhenDone", toolType, null)
    toolList.contents += new ToolButton("In5Seconds", toolType, null)
    toolList.contents += new ToolButton("OnFoodNearby", toolType, null)
    toolList.contents += new ToolButton("OnFoodConsumed", toolType, null)
    toolList.contents += new ToolButton("OnMobNearby", toolType, null)
    toolList.contents += new ToolButton("OnMobMoved", toolType, null)
    toolList.contents += new ToolButton("OnMobGone", toolType, null)

}

class SettersToolPanel
        extends ToolPanel("Setters", SetterToolType) {

    // Set Speed
    toolList.contents += new ToolButton("Speed", toolType, null)
    // Set Direction
    toolList.contents += new ToolButton("Direction", toolType, null)
    // Set any Variable
    toolList.contents += new ToolButton("Set", toolType, new AssignmentStatementPanelController(new AssignmentStatementPanel))
    // Say something
    toolList.contents += new ToolButton("Say", toolType, new PrintStatementPanelController(new PrintStatementPanel))

}

class ToolButton(val title: String, val toolType: ToolType, val controller: GlassPanelController[Expression[Any]])
        extends Button {

    text = title

    listenTo(mouse.clicks)
    reactions += {
        case event: MousePressed =>
            GlassIDE.eventBus.post(new ToolDraggedEvent(title, toolType, event.point, controller))
        case event: MouseReleased =>
            GlassIDE.eventBus.post(new ToolDroppedEvent(title, toolType, event.point, controller))
    }

}

class ToolDraggedEvent(val tool: String, val toolType: ToolType, val point: Point, val controller: GlassPanelController[Expression[Any]])
    extends Event

class ToolDroppedEvent(val tool: String, val toolType: ToolType, val point: Point, val controller: GlassPanelController[Expression[Any]])
    extends Event

sealed trait ToolType

object EventToolType extends ToolType

object SetterToolType extends ToolType
