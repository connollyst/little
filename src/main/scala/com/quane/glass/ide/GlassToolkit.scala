package com.quane.glass.ide

import java.awt.Dimension
import java.awt.Point
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.GlassPanelFactory
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.ide.language.ExpressionPanelController

class ToolkitPanel
        extends BoxPanel(Orientation.Vertical) {

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

    toolList.contents += new ToolButton("OnSpawn", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnSpawn)
    )
    toolList.contents += new ToolButton("OnContact", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnContact)
    )
    toolList.contents += new ToolButton("OnContactEnded", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnContactEnded)
    )
    toolList.contents += new ToolButton("WhenDone", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.WhenDone)
    )
    toolList.contents += new ToolButton("In5Seconds", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.In5Seconds)
    )
    toolList.contents += new ToolButton("OnFoodNearby", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnFoodNearby)
    )
    toolList.contents += new ToolButton("OnFoodConsumed", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnFoodConsumed)
    )
    toolList.contents += new ToolButton("OnMobNearby", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnMobNearby)
    )
    toolList.contents += new ToolButton("OnMobMoved", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnMobMoved)
    )
    toolList.contents += new ToolButton("OnMobGone", toolType,
        GlassPanelFactory.getEventFrameFunction(GlassEvent.OnMobGone)
    )

}

class SettersToolPanel
        extends ToolPanel("Setters", SetterToolType) {

    // Say something
    toolList.contents += new ToolButton("Say", toolType, GlassPanelFactory.createPrintStatementPanel)
    // Set Speed
    toolList.contents += new ToolButton("Speed", toolType, GlassPanelFactory.createSetSpeedStatementPanel)
    // Set Direction
    toolList.contents += new ToolButton("Direction", toolType, GlassPanelFactory.createSetDirectionStatementPanel)
    // Set any Variable
    toolList.contents += new ToolButton("Set", toolType, GlassPanelFactory.createAssignmentStatementPanel)

}

class ToolButton(val title: String,
                 val toolType: ToolType,
                 val dropFunction: () => ExpressionPanelController[Expression[Any]])
        extends Button {

    text = title

    listenTo(mouse.clicks)
    reactions += {
        case event: MousePressed =>
            GlassIDE.eventBus.post(
                new ToolDraggedEvent(title, toolType, event.point, dropFunction)
            )
        case event: MouseReleased =>
            GlassIDE.eventBus.post(
                new ToolDroppedEvent(title, toolType, event.point, dropFunction)
            )
    }

}

class ToolDraggedEvent(val tool: String,
                       val toolType: ToolType,
                       val point: Point,
                       val controllerFactoryFunction: () => ExpressionPanelController[Expression[Any]])
        extends Event

class ToolDroppedEvent(val tool: String,
                       val toolType: ToolType,
                       val point: Point,
                       val controllerFactoryFunction: () => ExpressionPanelController[Expression[Any]])
        extends Event

sealed trait ToolType

object EventToolType extends ToolType

object SetterToolType extends ToolType
