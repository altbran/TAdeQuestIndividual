package main

trait Tarea extends ModificacionDeStats {

  def facilidad(eq: Equipo)(heroe: Heroe): Int = 0
  def ejecutadaPor(equipo: Equipo)(heroe: Heroe) = equipo.reemplazarMiembro(heroe, heroe.copy(tareasRealizadas = heroe.tareasRealizadas.+:(this)))
  def efectoPara(heroe: Heroe)(stat: Stat): Stat 
}

class NoPuedeRealizarTarea(val tarea: Tarea) extends Exception

case object pelearContraMonstruo extends Tarea {

  override def efectoPara(heroe: Heroe)(stat: Stat): Stat = {
    if (stat.fuerza < 20)
      cambiarHpEn(-10)(stat)
    else
      stat
  }

  override def facilidad(eq: Equipo)(heroe: Heroe) = {
    eq.lider match {
      case Some(h) if (h.trabajo == Some(Guerrero)) => 20
      case Some(_)                                  => 10
      case _                                        => 10
    }
  }
}

case object forzarPuerta extends Tarea {

  override def efectoPara(heroe: Heroe)(stat: Stat): Stat = {
    heroe.trabajo match {
      case Some(Mago)   => stat
      case Some(Ladron) => stat
      case _            => (cambiarHpEn(-5)_ compose cambiarFuerzaEn(1)_)(stat)
    }
  }

  override def facilidad(eq: Equipo)(heroe: Heroe) = heroe.getInteligencia + eq.cantidadDe(Ladron) * 10
}

case class robarTalisman(talisman: Talisman) extends Tarea {

    override def efectoPara(heroe: Heroe)(stat: Stat): Stat = stat

    override def ejecutadaPor(equipo: Equipo)(heroe: Heroe) = {
      equipo.reemplazarMiembro(heroe, heroe.copy(items = heroe.items.+:(talisman), tareasRealizadas = heroe.tareasRealizadas.+:(this)))
    }

    override def facilidad(eq: Equipo)(heroe: Heroe) = {
      eq.lider match {
        case Some(h) if (h.trabajo == Some(Ladron)) => h.getVelocidad
        case _                                      => throw new NoPuedeRealizarTarea(this)
      }
    }
  
}
 