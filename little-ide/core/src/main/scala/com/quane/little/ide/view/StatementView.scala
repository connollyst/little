package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ExpressionPresenter, FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}


trait GetStatementView extends ExpressionView[GetStatementViewPresenter] {

  def setName(name: String): Unit

}

trait SetStatementView extends ExpressionView[SetStatementViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_]

  def createGetStatement(): GetStatementPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait PrintStatementView extends ExpressionView[PrintStatementViewPresenter] {

  def createValueStatement(): ValuePresenter[_]

  def createGetStatement(): GetStatementPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait GetStatementViewPresenter extends ExpressionViewPresenter {

  def onNameChange(name: String): Unit

}

trait SetStatementViewPresenter extends ExpressionViewPresenter {

  def onNameChange(name: String): Unit

  def onValueChange(p: ExpressionPresenter): Unit

}

trait PrintStatementViewPresenter extends ExpressionViewPresenter {

  def onValueChange(p: ExpressionPresenter): Unit

}