package com.quane.little.ide

import java.awt.Dimension
import java.awt.Point

import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.Component
import scala.swing.Label
import scala.swing.Orientation
import scala.swing.event.Event
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased

import com.quane.little.ide.dnd.DragAndDropItem
import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Expression
import com.quane.little.language.event.LittleEvent

class ToolkitPanel
        extends BoxPanel(Orientation.Vertical) {

    preferredSize = new Dimension(200, 600);
    minimumSize = preferredSize;

    val eventsToolPanel = new EventsToolPanel
    val settersToolPanel = new SettersToolPanel
    val gettersToolPanel = new GettersToolPanel
    val mathToolPanel = new MathToolPanel

    contents += eventsToolPanel
    contents += settersToolPanel
    contents += gettersToolPanel
    contents += mathToolPanel
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
        PanelFactory.getEventFrameFunction(LittleEvent.OnSpawn)
    )
    toolList.contents += new ToolButton("OnContact", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnContact)
    )
    toolList.contents += new ToolButton("OnContactEnded", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnContactEnded)
    )
    toolList.contents += new ToolButton("WhenDone", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.WhenDone)
    )
    toolList.contents += new ToolButton("In5Seconds", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.In5Seconds)
    )
    toolList.contents += new ToolButton("OnFoodNearby", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnFoodNearby)
    )
    toolList.contents += new ToolButton("OnFoodConsumed", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnFoodConsumed)
    )
    toolList.contents += new ToolButton("OnMobNearby", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnMobNearby)
    )
    toolList.contents += new ToolButton("OnMobMoved", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnMobMoved)
    )
    toolList.contents += new ToolButton("OnMobGone", toolType,
        PanelFactory.getEventFrameFunction(LittleEvent.OnMobGone)
    )
}

class SettersToolPanel
        extends ToolPanel("Setters", SetterToolType) {

    toolList.contents += new ToolButton("Set Boolean", toolType,
        PanelFactory.createSetBoolStatementPanel)

    toolList.contents += new ToolButton("Set Number", toolType,
        PanelFactory.createSetNumberStatementPanel)

    toolList.contents += new ToolButton("Set Text", toolType,
        PanelFactory.createSetTextStatementPanel)

    toolList.contents += new ToolButton("Set Speed", toolType,
        PanelFactory.createSetSpeedStatementPanel)

    toolList.contents += new ToolButton("Set Direction", toolType,
        PanelFactory.createSetDirectionStatementPanel)
}

class GettersToolPanel
        extends ToolPanel("Getters", GetterToolType) {

    toolList.contents += new ToolButton("Get Bool", GetBoolToolType,
        PanelFactory.createGetBoolStatementPanel)

    toolList.contents += new ToolButton("Any Bool", GetBoolToolType,
            PanelFactory.createBoolFieldPanel)
    
    toolList.contents += new ToolButton("Get Number", GetNumberToolType,
        PanelFactory.createGetNumberStatementPanel)

    toolList.contents += new ToolButton("Any Number", GetNumberToolType,
            PanelFactory.createNumberFieldPanel)
    
    toolList.contents += new ToolButton("Get Text", GetTextToolType,
        PanelFactory.createGetTextStatementPanel)
    
    toolList.contents += new ToolButton("Any Text", GetTextToolType,
            PanelFactory.createTextFieldPanel)
}

class MathToolPanel
        extends ToolPanel("Math", GetNumberToolType) {

    toolList.contents += new ToolButton("Math", toolType,
        PanelFactory.createMathPanel)
}

class ToolButton(val title: String,
                 val toolType: ToolType,
                 val dropFunction: () => ExpressionController[_ <: Expression[_]])
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
                       val dropFunction: () => ExpressionController[Expression[Any]])
        extends Event

class ToolDroppedEvent(val source: Component,
                       val toolType: ToolType,
                       val point: Point,
                       val dropFunction: () => ExpressionController[Expression[Any]])
        extends Event

sealed trait ToolType extends DragAndDropItem

object EventToolType extends ToolType

object SetterToolType extends ToolType

object GetterToolType extends ToolType

object GetBoolToolType extends ToolType

object GetNumberToolType extends ToolType

object GetTextToolType extends ToolType