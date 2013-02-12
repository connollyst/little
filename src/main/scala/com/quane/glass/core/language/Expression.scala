package com.quane.glass.core.language

import com.quane.glass.core.language.data.Value

/** @author Sean Connolly
  */
trait Expression[+T] {

    def evaluate: T

}