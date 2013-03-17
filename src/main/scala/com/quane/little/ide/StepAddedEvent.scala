package com.quane.little.ide

import scala.swing.event.Event
import com.quane.little.ide.language.ExpressionController
import com.quane.little.ide.language.FunctionController
import com.quane.little.ide.language.statement.GetStatementController
import com.quane.little.ide.language.ListenerController
import com.quane.little.ide.language.statement.SetStatementController
import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import com.quane.little.language.data.Text
import com.quane.little.ide.language.statement.GetTextStatementController

class StepAddedEvent(val controller: ExpressionController[Expression[Any]])
    extends Event

class ListenerAddedEvent(override val controller: ListenerController,
                         val toolType: ToolType,
                         val x: Int,
                         val y: Int)
        extends StepAddedEvent(controller)

class FunctionAddedEvent(override val controller: FunctionController,
                         val toolType: ToolType,
                         val x: Int,
                         val y: Int)
        extends StepAddedEvent(controller)

class GetterAddedEvent[V <: Value](override val controller: GetStatementController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)

class GetTextStatementAddedEvent(override val controller: GetTextStatementController,
                                 override val toolType: ToolType,
                                 override val x: Int,
                                 override val y: Int)
        extends GetterAddedEvent[Text](controller, toolType, x, y)

class SetterAddedEvent[V <: Value](override val controller: SetStatementController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)