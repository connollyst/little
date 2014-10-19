package com.quane.little.language.math

import com.quane.little.language.Code
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType

trait Math extends Code {

  // TODO why only integer?
  override def returnType: ValueType = ValueType.Number

}
