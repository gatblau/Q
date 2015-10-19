package org.gatblau.q

import org.gatblau.q.util.Cache

object StringUtils {
   implicit class MergeFromCache(str: String) {
     def %(args: String*): String = {
       val values = new Array[String](args.length)
       var i = 0
       args.foreach { arg =>
         values(i) = Cache.get(arg).toString()
         i += 1
       }
       str.format(values :_*)
     }
   }
}
