package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.escalatesoft.subcut.inject.BindingModule

/**
 *
 *
 * @author Sean Connolly
 */
class PresenterFactory(implicit val bindingModule: BindingModule) {

  // TODO views should create views, presenters create presenters to wrap them

  // TODO generify ViewPresenter?
  def create[P <: ViewPresenter, V <: View[P]](view: V): ViewPresenter = {
    view match {
      case ide: IDEView => new IDEPresenter(ide)
      case block: BlockView => new BlockPresenter(block)
      // TODO can we ensure this is complete?
    }
  }

  // TODO generify this into the above function

  def createValuePresenter(view: ValueView) = new ValuePresenter(view)

  def createSetPresenter(view: SetterView) = new SetterPresenter(view)

  def createGetPresenter(view: GetterView) = new GetterPresenter(view)

  def createArgument(view: FunctionArgumentView) = new FunctionArgumentPresenter(view)

  def createFunctionReference(view: FunctionReferenceView) = new FunctionReferencePresenter(view)

  def createFunctionParameter(view: FunctionParameterView) = new FunctionParameterPresenter(view)

}
