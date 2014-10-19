package com.quane.little.ide.presenter.command

import com.quane.little.data.model.{FunctionId, ListenerId, Id}
import com.quane.little.ide.presenter._

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

class AddCodeCommand(receiver: PresenterAcceptsCode, id: Id, index: Int = 0)
  extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddCode(id, index)
}

class AddFunctionDefinitionCommand(receiver: PresenterAcceptsFunctionDefinition, id: FunctionId, index: Int = 0)
  extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddFunctionDefinition(id, index)
}

class AddEventListenerCommand(receiver: PresenterAcceptsEventListener, id: ListenerId, index: Int = 0)
  extends IDECommand {
  override private[presenter] def execute() = receiver.requestAddEventListener(id, index)
}
