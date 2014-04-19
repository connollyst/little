package com.quane.little.ide.presenter.command

/** An executor for [[IDECommand]] instances. Maintains a history of commands
  * executed for easy undos.
  *
  * @author Sean Connolly
  */
object IDECommandExecutor {

  // TODO replace object with a Guice managed dependency or something

  // TODO stash in history for undo support
  def execute(command: IDECommand): Unit = command.execute()

}
