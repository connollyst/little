package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.escalatesoft.subcut.inject.BindingModule

/** A factory for creating presenters.
  *
  * @author Sean Connolly
  */
class PresenterFactory(implicit val bindingModule: BindingModule) {

  // TODO generify ViewPresenter?
  def create[P <: ViewPresenter, V <: View[P]](view: V): ViewPresenter = {
    view match {
      case ide: IDEView => new IDEPresenter(ide)
      case block: BlockView => new BlockPresenter(block)
      // TODO can we ensure this is complete?
    }
  }

  // TODO generify all of these into the above function

  def createCodeMenu[C <: PresenterAccepts](view: CodeMenuView, context: C)(implicit m: Manifest[C]) = new CodeMenuPresenter(view, context)

  def createMathPresenter(view: MathView) = new MathPresenter(view)

  def createValuePresenter(view: ValueView) = new ValuePresenter(view)

  def createSetPresenter(view: SetterView) = new SetterPresenter(view)

  def createGetPresenter(view: GetterView) = new GetterPresenter(view)

  def createPrintPresenter(view: PrinterView) = new PrinterPresenter(view)

  def createLogicPresenter(view: LogicView) = new LogicPresenter(view)

  def createConditionalPresenter(view: ConditionalView) = new ConditionalPresenter(view)

  def createFunctionArgument(view: FunctionArgumentView) = new FunctionArgumentPresenter(view)

  def createFunctionReference(view: FunctionReferenceView) = new FunctionReferencePresenter(view)

  def createFunctionParameter(view: FunctionParameterView) = new FunctionParameterPresenter(view)

}
