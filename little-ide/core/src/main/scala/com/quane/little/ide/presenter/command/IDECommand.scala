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

class AddValueCommand(receiver: PresenterAcceptsValue, index: Int = 0) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddTextLiteral(index)

}

class AddGetterCommand(receiver: PresenterAcceptsGetter, index: Int = 0) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddGetStatement(index)

}

class AddFunctionReferenceCommand(receiver: PresenterAcceptsFunctionReference, id: RecordId, index: Int = 0) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddFunctionReference(id, index)

}

class AddFunctionDefinitionCommand(receiver: PresenterAcceptsFunctionDefinition, id: RecordId, index: Int = 0) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddFunctionDefinition(id, index)

}

class AddEventListenerCommand(receiver: PresenterAcceptsEventListener, id: RecordId, index: Int = 0) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddEventListener(id, index)

}