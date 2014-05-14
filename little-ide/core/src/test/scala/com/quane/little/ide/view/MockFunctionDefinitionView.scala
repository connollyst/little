package com.quane.little.ide.view

import com.escalatesoft.subcut.inject.BindingModule

/** An implementation of [[com.quane.little.ide.view.FunctionDefinitionView]]
  * for testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionDefinitionView(implicit val bindingModule: BindingModule)
  extends FunctionDefinitionView
  with MockView {

  override def setName(name: String) = Unit

  override def createFunctionParameter() = new MockFunctionParameterView

  override def createBlock() = new MockBlockView

}
