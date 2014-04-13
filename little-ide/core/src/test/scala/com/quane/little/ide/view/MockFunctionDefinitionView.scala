package com.quane.little.ide.view

import com.quane.little.ide.presenter.{FunctionParameterPresenter, BlockPresenter}

/** An implementation of [[com.quane.little.ide.view.FunctionDefinitionView]]
  * for testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionDefinitionView
  extends FunctionDefinitionView
  with MockView {

  override def setName(name: String) = Unit

  override def createFunctionParameter() = new FunctionParameterPresenter(new MockFunctionParameterView)

  override def createBlock() = new BlockPresenter(new MockBlockView)

}
