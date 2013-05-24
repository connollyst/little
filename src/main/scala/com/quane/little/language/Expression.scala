package com.quane.little.language

import com.quane.little.language.data.ValueTypeSafe

/** @author Sean Connolly
  */
trait Expression[+T] {

    def evaluate: T

}