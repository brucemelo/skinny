package controller

import skinny._
import skinny.filter._

/**
  * The base controller for this Skinny application.
  *
  * see also "https://skinny-framework.github.io/documentation/controller-and-routes.html"
  */
trait ApplicationController
    extends SkinnyController
    // with TxPerRequestFilter
    // with SkinnySessionFilter
    with ErrorPageFilter {

  // override def defaultLocale = Some(new java.util.Locale("ja"))

}
