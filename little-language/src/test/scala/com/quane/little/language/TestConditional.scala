package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.Assert._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the [[Conditional]] expression.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestConditional extends FlatSpec with ShouldMatchers {

  private val name = "OuterScopeVariable"
  private val defaultValue = Value("DefaultValue")
  private val thenValue = Value("ThenValue")
  private val otherwiseValue = Value("OtherwiseValue")
  private val positive = Value(true)
  private val negative = Value(false)

  "conditional expression" should "evaluate 'then' block" in {
    val snapshot = evaluateConditional(positive)
    val testValue = snapshot.scope.fetch(name).value
    assertEquals(thenValue, testValue.asText)
  }
  it should "evaluate 'otherwise' block" in {
    val snapshot = evaluateConditional(negative)
    val testValue = snapshot.scope.fetch(name).value
    assertEquals(otherwiseValue, testValue.asText)
  }
  it should "not store 'then' variables in outer scope" in {
    val name = "InnerScopeVariable"
    val then = new Block
    val otherwise = new Block
    then += new Setter(name, thenValue)
    otherwise += new Setter(name, otherwiseValue)
    val snapshot = evaluateConditional(positive, then, otherwise)
    snapshot.scope.contains(name) should be(false)
  }
  it should "not store 'otherwise' variables in outer scope" in {
    val name = "InnerScopeVariable"
    val then = new Block
    val otherwise = new Block
    then += new Setter(name, thenValue)
    otherwise += new Setter(name, otherwiseValue)
    val snapshot = evaluateConditional(negative, then, otherwise)
    snapshot.scope.contains(name) should be(false)
  }

  /** Creates a [[Conditional]] expression using the provided `test`.
    *
    * A snapshot is returned which can be inspected to assert the conditional
    * functionality.
    *
    * @param test the test expression
    * @return a snapshot of the scope following the evaluation
    */
  private def evaluateConditional(test: EvaluableCode): ScopeSnapshot = {
    val then = new Block
    val otherwise = new Block
    then += new Setter(name, thenValue)
    otherwise += new Setter(name, otherwiseValue)
    evaluateConditional(test, then, otherwise)
  }

  /** Creates a [[Conditional]] expression using the provided `test`, `then`,
    * &amp; `otherwise` expressions.
    *
    * A snapshot is returned which can be inspected to assert the conditional
    * functionality.
    *
    * @param test the test expression
    * @param then the then block
    * @param otherwise the otherwise block
    * @return  a snapshot of the scope following the evaluation
    */
  private def evaluateConditional(test: EvaluableCode, then: Block, otherwise: Block): ScopeSnapshot = {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block += new Setter(name, defaultValue)
    block += new Conditional(test, then, otherwise)
    block += snapshot
    block.evaluate(new Runtime)
    snapshot
  }

}