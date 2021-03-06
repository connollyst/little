package com.quane.little.ide.presenter

import com.quane.little.data.model.Id
import com.quane.little.data.service.CodeService
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.language.math.BasicMath

/** A trait shared by presenters who manage two expressions, one being thought
  * of as the 'left' and the other as the 'right'. For example, a mathematical
  * operation with left and right operands.
  *
  * @author Sean Connolly
  */
trait PresenterOfLeftAndRightExpressions {

  private var _left: Option[CodeViewPresenter] = None
  private var _right: Option[CodeViewPresenter] = None

  protected def presenterFactory: PresenterFactory

  protected def codeService: CodeService

  protected def view: ViewOfLeftAndRightExpressions

  private[presenter] def left: CodeViewPresenter = {
    _left match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No left operand expression set.")
    }
  }

  private[presenter] def right: CodeViewPresenter = {
    _right match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No right operand expression set.")
    }
  }

  /** Set the left operand expression.
    *
    * @param e the new left operand expression
    */
  private[presenter] def left_=(e: Code): Unit = {
    val presenter =
      e match {
        case v: Value =>
          presenterFactory.createValuePresenter(view.createLeftLiteral()).initialize(v)
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createLeftGetter()).initialize(g)
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createLeftMath()).initialize(m)
        case l: Logic =>
          presenterFactory.createLogicPresenter(view.createLeftLogic()).initialize(l)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createLeftFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _left = Some(presenter)
  }

  /** Set the left operand expression.
    *
    * @param e the new left operand expression
    */
  private[presenter] def right_=(e: Code): Unit = {
    val presenter =
      e match {
        case v: Value =>
          presenterFactory.createValuePresenter(view.createRightLiteral()).initialize(v)
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createRightGetter()).initialize(g)
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createRightMath()).initialize(m)
        case l: Logic =>
          presenterFactory.createLogicPresenter(view.createRightLogic()).initialize(l)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createRightFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _right = Some(presenter)
  }

  /** Set the left or right expression to the [[com.quane.little.language.FunctionReference]]
    * specified by the `id`
    *
    * @param id the function id
    * @param index `0` for the left operand, `1` for the right
    * @throws IllegalArgumentException for an index other than `0` or `1`
    */
  def requestAddCode(id: Id, index: Int) =
    index match {
      case 0 => left = find(id)
      case 1 => right = find(id)
      case _ => throw new IllegalArgumentException(
        "Invalid left/right index " + index + ", expected 0 or 1"
      )
    }

  private def find(id: Id): Code =
    codeService.find(id) match {
      case Some(c) => c
      case None => throw new IllegalArgumentException("Node code found for id=" + id.id)
    }

}
