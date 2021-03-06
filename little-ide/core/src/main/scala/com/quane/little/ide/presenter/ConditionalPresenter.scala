package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.google.common.base.Objects
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.math.BasicMath

/** A presenter for views representing an [[com.quane.little.language.Conditional]].
  *
  * @author Sean Connolly
  */
class ConditionalPresenter[V <: ConditionalView](view: V)(implicit val bindingModule: BindingModule)
  extends ConditionalViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]

  private var _condition: Option[CodeViewPresenter] = None
  private val _thenBlock = new BlockPresenter(view.createThenBlock())
  private val _elseBlock = new BlockPresenter(view.createElseBlock())

  view.registerViewPresenter(this)

  private[presenter] def initialize(c: Conditional): ConditionalPresenter[V] = {
    condition = c.test
    thenBlock = c.then.steps
    elseBlock = c.otherwise.steps
    this
  }

  private[presenter] def condition: CodeViewPresenter =
    _condition match {
      case Some(e) => e
      case _ => throw new IllegalAccessException("No condition expression set.")
    }

  private[presenter] def condition_=(condition: Code): Unit = {
    val presenter =
      condition match {
        case l: Logic =>
          val stepView = view.createLogicCondition()
          presenterFactory.createLogicPresenter(stepView).initialize(l)
        case m: BasicMath =>
          val stepView = view.createMathCondition()
          presenterFactory.createMathPresenter(stepView).initialize(m)
        case g: Getter =>
          val stepView = view.createGetterCondition()
          presenterFactory.createGetPresenter(stepView).initialize(g)
        case c: Conditional =>
          val stepView = view.createConditionalCondition()
          presenterFactory.createConditionalPresenter(stepView).initialize(c)
        case f: FunctionReference =>
          val stepView = view.createFunctionReferenceCondition()
          presenterFactory.createFunctionReference(stepView).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + condition)
      }
    _condition = Some(presenter)
  }

  private[presenter] def thenBlock: List[CodeViewPresenter] = _thenBlock.steps

  private[presenter] def thenBlock_=(steps: List[Code]): Unit = _thenBlock.steps = steps

  private[presenter] def elseBlock: List[CodeViewPresenter] = _elseBlock.steps

  private[presenter] def elseBlock_=(steps: List[Code]): Unit = _elseBlock.steps = steps

  override def compile(): Conditional =
    new Conditional(condition.compile(), _thenBlock.compile(), _elseBlock.compile())

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("condition", condition)
      .add("then", thenBlock)
      .add("else", elseBlock)
      .toString

}