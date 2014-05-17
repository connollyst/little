package com.quane.little.ide.view

import com.escalatesoft.subcut.inject.BindingModule

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView(implicit val bindingModule: BindingModule)
  extends BlockView
  with MockView {

  def addMath(): MathView = new MockMathView

  def addMath(index: Int): MathView = new MockMathView

  def addConditional() = new MockConditionalView

  def addConditional(index: Int) = new MockConditionalView

  def addGetStatement() = new MockGetterView

  def addGetStatement(index: Int) = new MockGetterView

  def addSetStatement() = new MockSetterView

  def addSetStatement(index: Int) = new MockSetterView

  def addPrintStatement() = new MockPrinterView

  def addPrintStatement(index: Int) = new MockPrinterView

  def addFunctionReference() = new MockFunctionReferenceView

  def addFunctionReference(index: Int) = new MockFunctionReferenceView

}
