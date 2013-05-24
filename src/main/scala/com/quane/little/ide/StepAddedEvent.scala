package com.quane.little.ide

import scala.swing.event.Event

import com.quane.little.ide.language.ExpressionController
import com.quane.little.ide.language.FunctionController
import com.quane.little.ide.language.ListenerController
import com.quane.little.ide.language.statement.GetBoolStatementController
import com.quane.little.ide.language.statement.GetNumberStatementController
import com.quane.little.ide.language.statement.GetStatementController
import com.quane.little.ide.language.statement.GetTextStatementController
import com.quane.little.ide.language.statement.SetStatementController
import com.quane.little.language.Expression
import com.quane.little.language.data.Bool
import com.quane.little.language.data.Number
import com.quane.little.language.data.Text
import com.quane.little.language.data.ValueTypeSafe
import com.quane.little.ide.dnd.ToolType

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

class GetterAddedEvent[V <: ValueTypeSafe](override val controller: GetStatementController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)

class GetBoolStatementAddedEvent(override val controller: GetBoolStatementController,
                                 override val toolType: ToolType,
                                 override val x: Int,
                                 override val y: Int)
        extends GetterAddedEvent[Bool](controller, toolType, x, y)

class GetNumberStatementAddedEvent(override val controller: GetNumberStatementController,
                                   override val toolType: ToolType,
                                   override val x: Int,
                                   override val y: Int)
        extends GetterAddedEvent[Number](controller, toolType, x, y)

class GetTextStatementAddedEvent(override val controller: GetTextStatementController,
                                 override val toolType: ToolType,
                                 override val x: Int,
                                 override val y: Int)
        extends GetterAddedEvent[Text](controller, toolType, x, y)

class SetterAddedEvent[V <: ValueTypeSafe](override val controller: SetStatementController[V],
                                   val toolType: ToolType,
                                   val x: Int,
                                   val y: Int)
        extends StepAddedEvent(controller)