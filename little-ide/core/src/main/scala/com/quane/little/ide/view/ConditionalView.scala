package com.quane.little.ide.view

trait ConditionalView extends CodeView[ConditionalViewPresenter] {

  def createMathCondition(): MathView

  def createGetterCondition(): GetterView

  def createLogicCondition(): LogicView

  def createConditionalCondition(): ConditionalView

  def createFunctionReferenceCondition(): FunctionReferenceView

  def createThenBlock(): BlockView

  def createElseBlock(): BlockView

}

trait ConditionalViewPresenter extends CodeViewPresenter