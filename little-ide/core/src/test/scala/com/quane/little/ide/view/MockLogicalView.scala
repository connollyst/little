package com.quane.little.ide.view

import com.escalatesoft.subcut.inject.BindingModule
import com.quane.little.language.EvaluationOperator.EvaluationOperator

class MockLogicalView(implicit val bindingModule: BindingModule)
  extends LogicalView
  with MockView {

  def setOperation(operation: EvaluationOperator) = Unit


  def createLeftValueStatement() = new MockValueView

  def createRightValueStatement() = new MockValueView


  def createLeftGetStatement() = new MockGetterView

  def createRightGetStatement() = new MockGetterView


  def createLeftFunctionReference() = new MockFunctionReferenceView

  def createRightFunctionReference() = new MockFunctionReferenceView

}
