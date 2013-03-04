package com.quane.little.ide

import scala.swing.event.Event
import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.ide.language.FunctionPanelController
import com.quane.little.ide.language.GetStatementPanelController
import com.quane.little.ide.language.ListenerPanelController
import com.quane.little.ide.language.SetStatementPanelController
import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import com.quane.little.language.data.Text

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

class GetterAddedEvent[V <: Value](override val controller: GetStatementPanelController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)

class GetTextStatementAddedEvent(override val controller: GetStatementPanelController[Text],
                                 override val toolType: ToolType,
                                 override val x: Int,
                                 override val y: Int)
        extends GetterAddedEvent[Text](controller, toolType, x, y)

class SetterAddedEvent[V <: Value](override val controller: SetStatementPanelController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)