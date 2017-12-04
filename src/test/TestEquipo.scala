package test

import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import main._
import junit.framework.AssertionFailedError

class TestEquipo {
  val statsBuenos = Stat(20, 20, 20, 20)
  val statsMalos = Stat()

  var heroeBueno: Heroe = null
  var inventario: Inventario = Inventario(List(cascoVikingo, armaduraEleganteSport), heroeBueno)

  
  val heroeMalo = new Heroe(statsMalos)

  var equipo: Equipo = null

  @Before
  def setUp() {
    heroeBueno = new Heroe(statsBuenos, Some(Ladron), inventario.items,List())
    equipo = new Equipo(integrantes = List(heroeBueno, heroeMalo))
  }

  @Test
  def mejorHeroeSegunFunciona() {
    assertEquals(equipo.mejorHeroeSegun{ _.getFuerza }, heroeBueno)
  }
  
  @Test
  def seAgregaMiembroNuevo() {
    val otro = new Heroe(Stat(5,5,5,5))
    assert(equipo.obtenerMiembro(otro).integrantes.contains(otro))
  }
  
  @Test
  def sePuedeReemplazarMiembro() {
    val otro = new Heroe(Stat(5,5,5,5))
    val nuevoEquipo = equipo.reemplazarMiembro(heroeMalo,otro)
    
    assert(nuevoEquipo.integrantes.contains(otro))
    assert(!nuevoEquipo.integrantes.contains(heroeMalo))
  }
  
  @Test
  def elLiderEsElHeroeBueno() {
    
    assertEquals(equipo.lider,Some(heroeBueno))
  }
  
  @Test
  def noHayLiderClaroEnAlgunosCasos() {
    val otro = heroeBueno.copy()
    
    assertEquals(equipo.obtenerMiembro(otro).lider,None)
  }
}