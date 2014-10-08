package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType

trait Math extends Expression {

  // TODO why only integer?
  override def returnType: ValueType = ValueType.Integer

}
