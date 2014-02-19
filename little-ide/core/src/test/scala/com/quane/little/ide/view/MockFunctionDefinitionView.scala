package com.quane.little.ide.view

import com.quane.little.ide.presenter.{FunctionParameterPresenter, BlockPresenter, FunctionReferencePresenter}

/** An implementation of [[com.quane.little.ide.view.FunctionDefinitionView]]
  * for testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionDefinitionView
  extends FunctionDefinitionView {

  def setName(name: String): Unit = Unit

  def createFunctionParameter(): FunctionParameterPresenter[MockFunctionParameterView] =
    new FunctionParameterPresenter(new MockFunctionParameterView)

  def createBlock(): BlockPresenter[MockBlockView] =
    new BlockPresenter(new MockBlockView)

  def createFunctionReference(): FunctionReferencePresenter[MockFunctionReferenceView] =
    new FunctionReferencePresenter(new MockFunctionReferenceView)

}
