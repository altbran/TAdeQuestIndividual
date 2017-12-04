package main

case class Taberna(misiones: List[Mision] = List(abrirPuerta)) {
  import main.CriteriosDeMision._
  
  
  def elegirMision(equipo: Equipo,criterio: Criterio) = {
    elegirMejorMisionPara(equipo, criterio)(misiones)
  }
  
  def entrenar(criterio: Criterio, equipo: Equipo) = { 
        funcRecursiva(criterio, Resultado(equipo), misiones).equipo
  }

  def funcRecursiva(criterio: Criterio, equipo: Resultado, listaMisiones: List[Mision]): Resultado = {
    val mision = elegirMejorMisionPara(equipo.get,criterio)(listaMisiones)
    
    if(mision.isDefined) {
      val nuevoEquipo = mision.fold(equipo)(_.serRealizadaPor(equipo))
      funcRecursiva(criterio,nuevoEquipo, listaMisiones.filterNot(x => mision.fold(false)(_.equals(x))))
    }
    else
       equipo
  }
  
  private def elegirMejorMisionPara(equipo: Equipo,criterio: Criterio)(listaMision: List[Mision]) = {
    var mejorEstado = cabezaONada(equipo)(estadosOrdenadosDeEquipo(equipo, criterio,listaMision))
    
    misiones.find(_.serRealizadaPor(Resultado(equipo)) == mejorEstado)
  }
  
  private def estadosOrdenadosDeEquipo(equipo: Equipo, criterio: Criterio,listaMision: List[Mision]) = {
    listaMision.map(_.serRealizadaPor(Resultado(equipo))).filter(_.isSuccess).map(_.get).sortWith((e1,e2) => criterio(e1,e2))
  }
  
  private def cabezaONada(equipo: Equipo)(lista: List[Equipo]) = Resultado(lista.headOption.fold(equipo)(eq => eq))
}

object CriteriosDeMision{
  type Criterio = (Equipo,Equipo) => Boolean
  
  val porMasOro = (equipo: Equipo, otroEquipo: Equipo) => equipo.pozo > otroEquipo.pozo
  
  val porMasHeroes = (equipo: Equipo, otroEquipo: Equipo) => equipo.integrantes.size > otroEquipo.integrantes.size
  
}