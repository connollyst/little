package com.quane.little.language.math

import com.quane.little.language.EvaluableCode
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType

trait Math extends EvaluableCode {

  // TODO why only integer?
  override def returnType: ValueType = ValueType.Integer

}
