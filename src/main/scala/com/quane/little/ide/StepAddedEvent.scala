package com.quane.little.ide

import scala.swing.event.Event
import com.quane.little.language.Expression
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.ide.language.ListenerPanelController
import com.quane.little.ide.language.FunctionPanelController

class StepAddedEvent(val controller: ExpressionPanelController[Expression[Any]])
    extends Event

class ListenerAddedEvent(override val controller: ListenerPanelController,
                         val toolType: ToolType,
                         val x: Int,
                         val y: Int)
        extends StepAddedEvent(controller)

class FunctionAddedEvent(override val controller: FunctionPanelController,
                         val toolType: ToolType,
                         val x: Int,
                         val y: Int)
        extends StepAddedEvent(controller)