package com.quane.glass.ide

import scala.swing.BoxPanel
import scala.swing.event.Event
import scala.swing.Button
import scala.swing.Reactor
import scala.swing.Orientation
import scala.swing.event.MouseClicked
import javax.swing.BorderFactory
import scala.swing.event.MousePressed
import java.awt.Dimension
import scala.swing.Label
import java.awt.Color
import scala.swing.event.MouseReleased
import scala.swing.event.MouseDragged
import scala.swing.event.MouseEvent
import java.awt.Point
import scala.swing.Component
import org.hamcrest.core.IsInstanceOf
import com.google.common.eventbus.EventBus

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

    toolList.contents += new ToolButton("OnSpawn", toolType)
    toolList.contents += new ToolButton("OnContact", toolType)
    toolList.contents += new ToolButton("OnContactEnded", toolType)
    toolList.contents += new ToolButton("WhenDone", toolType)
    toolList.contents += new ToolButton("In5Seconds", toolType)
    toolList.contents += new ToolButton("OnFoodNearby", toolType)
    toolList.contents += new ToolButton("OnFoodConsumed", toolType)
    toolList.contents += new ToolButton("OnMobNearby", toolType)
    toolList.contents += new ToolButton("OnMobMoved", toolType)
    toolList.contents += new ToolButton("OnMobGone", toolType)

}

class SettersToolPanel
        extends ToolPanel("Setters", SetterToolType) {

    toolList.contents += new ToolButton("Speed", toolType)
    toolList.contents += new ToolButton("Direction", toolType)

}

class ToolButton(val title: String, val toolType: ToolType)
        extends Button {

    text = title

    listenTo(mouse.clicks)
    reactions += {
        case event: MousePressed =>
            GlassIDE.eventBus.post(new ToolDraggedEvent(title, toolType, event.point))
        case event: MouseReleased =>
            GlassIDE.eventBus.post(new ToolDroppedEvent(title, toolType, event.point))
    }

}

class ToolDraggedEvent(val tool: String, val toolType: ToolType, val point: Point) extends Event

class ToolDroppedEvent(val tool: String, val toolType: ToolType, val point: Point) extends Event

sealed trait ToolType

object EventToolType extends ToolType

object SetterToolType extends ToolType
