package com.quane.glass.ide

import scala.swing.event.Event
import com.quane.glass.core.language.Expression
import com.quane.glass.ide.language.ExpressionPanelController
import com.quane.glass.ide.language.ListenerPanelController

class StepAddedEvent(val controller: ExpressionPanelController[Expression[Any]])
    extends Event

class ListenerAddedEvent(override val controller: ListenerPanelController,
                         val toolType: ToolType,
                         val x: Int,
                         val y: Int)
        extends StepAddedEvent(controller)