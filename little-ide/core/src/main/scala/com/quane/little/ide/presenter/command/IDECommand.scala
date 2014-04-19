package com.quane.little.ide.presenter.command

import com.quane.little.ide.presenter.AcceptsValue

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

class AddValueCommand(receiver: AcceptsValue) extends IDECommand {

  override private[presenter] def execute(): Unit = receiver.requestAddTextLiteral()

}