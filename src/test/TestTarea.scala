package test
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import main._
import junit.framework.AssertionFailedError

class TestTarea {
  var heroe: Heroe = null
  var ladriCompleto: Heroe = null
  var lider: Heroe = null
  var mago: Heroe = null
  var ladri: Heroe = null
  var equipo: Equipo = null
  var integrantes: List[Heroe] = null
  var integrantesLiderLadron: List[Heroe] = null
  var tareas: List[Tarea] = null
  var tarea: Tarea = null

  @Before
  def setUp() {
    tareas = List(pelearContraMonstruo)
    
    heroe = Heroe(Stat(10, 10, 10, 10), None, List(), tareas)
    ladriCompleto = Heroe(Stat(10, 10, 10, 10), Some(Ladron), List(armaduraEleganteSport), tareas)
    ladri = Heroe(Stat(15, 15, 15, 15), Some(Ladron), List(), List())
    mago = Heroe(Stat(15, 15, 15, 15), Some(Mago), List(), List())
    lider = Heroe(Stat(40, 10, 10, 10), Some(Guerrero), List(), List())

    integrantes = List(heroe, lider)
    tareas = List(pelearContraMonstruo)
    integrantesLiderLadron = List(ladriCompleto)

    tarea = new robarTalisman(talismanMinimalismo)

    equipo = Equipo("los power rangers", integrantes, 0)
  }

  @Test
  def testModificarStat() {
    assertEquals(Stat(1, 10, 10, 10), heroe.stats)
  }

  @Test
  def testReemplazarMiembroConTareaAgregada() {
    val heroeNuevo = Heroe(Stat(10, 10, 10, 10), None, List(talismanMinimalismo), List(tarea,pelearContraMonstruo))
    val equipoResultado = Equipo("los power rangers", List(heroeNuevo,lider), 0)
    assertEquals(equipoResultado, tarea.ejecutadaPor(equipo)(heroe))
  }

  @Test
  def testFacilidadConLiderGuerrero() {
    assertEquals(20, pelearContraMonstruo.facilidad(equipo)(heroe))
  }

  @Test
  def testFacilidadConLiderMago() {
    integrantes = mago :: integrantes
    assert(integrantes.contains(mago))
    assertEquals(10, pelearContraMonstruo.facilidad(Equipo("equipo auxiliar con mago", integrantes))(heroe))
  }

  @Test
  def testFacilidadConLadron() {
    integrantes = ladri :: integrantes
    assert(integrantes.contains(ladri))
    assertEquals(20, forzarPuerta.facilidad(Equipo("equipo auxiliar con ladron", integrantes))(heroe))
  }

  @Test
  def testFacilidadSinLider() {
    integrantes = ladri :: integrantes
    assert(integrantes.contains(ladri))
    assertEquals(10, forzarPuerta.facilidad(equipo)(heroe))
  }

  @Test
  def testFacilidadRobarTalisman() {
    val equipo = Equipo("equipo lider ladron", integrantesLiderLadron)
    
    assertEquals(50, tarea.facilidad(equipo)(ladriCompleto))

  }
} 