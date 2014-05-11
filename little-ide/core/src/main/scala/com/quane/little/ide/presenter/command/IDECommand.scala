package com.quane.little.ide.presenter.command

import com.quane.little.ide.presenter._
import com.quane.little.data.model.RecordId

/** An IDE command: an action, usually initiated by the user, which can be
  * executed and undone if necessary.
  *
  * @author Sean Connolly
  */
sealed trait IDECommand {

  /** Execute the command.
    */
  private[presenter] def execute(): Unit

}

class AddPrimitiveCommand(receiver: PresenterAcceptsPrimitive, id: RecordId, index: Int = 0) extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddPrimitive(id, index)
}

class AddFunctionReferenceCommand(receiver: PresenterAcceptsFunctionReference, id: RecordId, index: Int = 0) extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddFunctionReference(id, index)
}

class AddFunctionDefinitionCommand(receiver: PresenterAcceptsFunctionDefinition, id: RecordId, index: Int = 0) extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddFunctionDefinition(id, index)
}

class AddEventListenerCommand(receiver: PresenterAcceptsEventListener, id: RecordId, index: Int = 0) extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddEventListener(id, index)
}
