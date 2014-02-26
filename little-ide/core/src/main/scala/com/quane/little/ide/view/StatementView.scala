package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ExpressionPresenter, FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}


trait GetStatementView extends ExpressionView[GetStatementViewListener] {

  def setName(name: String): Unit

}

trait SetStatementView extends ExpressionView[SetStatementViewListener] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_]

  def createGetStatement(): GetStatementPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait PrintStatementView extends ExpressionView[PrintStatementViewListener] {

  def createValueStatement(): ValuePresenter[_]

  def createGetStatement(): GetStatementPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait GetStatementViewListener extends ExpressionViewListener {

  def nameChanged(name: String): Unit

}

trait SetStatementViewListener extends ExpressionViewListener {

  def nameChanged(name: String): Unit

  def setValueExpression(p: ExpressionPresenter): Unit

}

trait PrintStatementViewListener extends ExpressionViewListener {

  def setExpression(p: ExpressionPresenter): Unit

}