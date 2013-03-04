package com.quane.little.ide

import java.awt.Dimension
import java.awt.Point

import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Component
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MouseEvent
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased

import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.language.Expression
import com.quane.little.language.event.GlassEvent

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

    toolList.contents += new ToolButton("Function", toolType,
        PanelFactory.getFunctionFrameFunction()
    )
    toolList.contents += new ToolButton("OnSpawn", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnSpawn)
    )
    toolList.contents += new ToolButton("OnContact", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnContact)
    )
    toolList.contents += new ToolButton("OnContactEnded", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnContactEnded)
    )
    toolList.contents += new ToolButton("WhenDone", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.WhenDone)
    )
    toolList.contents += new ToolButton("In5Seconds", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.In5Seconds)
    )
    toolList.contents += new ToolButton("OnFoodNearby", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnFoodNearby)
    )
    toolList.contents += new ToolButton("OnFoodConsumed", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnFoodConsumed)
    )
    toolList.contents += new ToolButton("OnMobNearby", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnMobNearby)
    )
    toolList.contents += new ToolButton("OnMobMoved", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnMobMoved)
    )
    toolList.contents += new ToolButton("OnMobGone", toolType,
        PanelFactory.getEventFrameFunction(GlassEvent.OnMobGone)
    )

}

class SettersToolPanel
        extends ToolPanel("Setters", SetterToolType) {

    //    toolList.contents += new ToolButton("Say", toolType, PanelFactory.createPrintStatementPanel)
    toolList.contents += new ToolButton("Set Speed", toolType, PanelFactory.createSetSpeedStatementPanel)
    toolList.contents += new ToolButton("Set Direction", toolType, PanelFactory.createSetDirectionStatementPanel)
    toolList.contents += new ToolButton("Set Text", toolType, PanelFactory.createSetTextWithFieldStatementPanel)
    toolList.contents += new ToolButton("Set Text (Expression)", toolType, PanelFactory.createSetTextWithExpressionStatementPanel)

}

class ToolButton(val title: String,
                 val toolType: ToolType,
                 val dropFunction: () => ExpressionPanelController[_ <: Expression[_]])
        extends Button {

    text = title

    listenTo(mouse.clicks)
    reactions += {
        case event: MousePressed =>
            IDE.eventBus.post(
                new ToolDraggedEvent(this, toolType, event.point, dropFunction)
            )
        case event: MouseReleased =>
            IDE.eventBus.post(
                new ToolDroppedEvent(this, toolType, event.point, dropFunction)
            )
    }

}

class ToolDraggedEvent(val source: Component,
                       val toolType: ToolType,
                       val point: Point,
                       val dropFunction: () => ExpressionPanelController[Expression[Any]])
        extends Event

class ToolDroppedEvent(val source: Component,
                       val toolType: ToolType,
                       val point: Point,
                       val dropFunction: () => ExpressionPanelController[Expression[Any]])
        extends Event

sealed trait ToolType extends DragAndDropItem

object EventToolType extends ToolType

object SetterToolType extends ToolType

object NumberToolType extends ToolType

object TextToolType extends ToolType