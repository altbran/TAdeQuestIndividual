package test

import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import main._
import junit.framework.AssertionFailedError

class TestStats {
  
  @Test
  def companionObjectFunciona() {
    assertEquals(Stat(10,1,1,1), Stat(10,1,1,1))
  }
  
  @Test
  def statsConAtributosNegativosSeCorrigen() {
    assertEquals(Stat(-10,-10,-10,-10), Stat(1,1,1,1))
  }
  
  @Test
  def statPrincipalEsElMayor() {
    assertEquals(Stat(-10,100,-10,-10).statPrincipal, 100)
  }
  
  @Test
  def metodoCopyFuncionaBien() {
    assertEquals(Ladron.apply(Stat(1,1,1,1)), Stat(1,1,11,1))
  }
  
}