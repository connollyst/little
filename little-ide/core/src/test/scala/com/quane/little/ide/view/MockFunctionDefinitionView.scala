package com.quane.little.ide.view

import com.quane.little.ide.presenter.{FunctionParameterPresenter, BlockPresenter, FunctionReferencePresenter}

/** An implementation of [[com.quane.little.ide.view.FunctionDefinitionView]]
  * for testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionDefinitionView
  extends FunctionDefinitionView
  with MockView {

  def setName(name: String) = Unit

  def createFunctionParameter() = new FunctionParameterPresenter(new MockFunctionParameterView)

  def createBlock() = new BlockPresenter(new MockBlockView)

  def createFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
