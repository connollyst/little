package com.quane.little.ide.view

object MockConditionalView {
  def apply(): MockConditionalView = new MockConditionalView
}

/** An implementation of [[com.quane.little.ide.view.ConditionalView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockConditionalView
  extends ConditionalView
  with MockView {

  def createLogicCondition() = new MockLogicView

  def createMathCondition() = new MockMathView

  def createGetterCondition() = new MockGetterView

  def createConditionalCondition() = new MockConditionalView()

  def createFunctionReferenceCondition() = new MockFunctionReferenceView

  def createThenBlock() = new MockBlockView

  def createElseBlock() = new MockBlockView

}
