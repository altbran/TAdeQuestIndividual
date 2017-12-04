package main

trait ModificacionDeStats {
  protected def cambiarFuerzaEn(valor: Int)(stat: Stat) = stat.copy(fuerza = stat.fuerza + valor)
  protected def cambiarHpEn(valor: Int)(stat: Stat) = stat.copy(hp = stat.hp + valor)
  protected def cambiarVelocidadEn(valor: Int)(stat: Stat) = stat.copy(velocidad = stat.velocidad + valor)
  protected def cambiarInteligenciaEn(valor: Int)(stat: Stat) = stat.copy(inteligencia = stat.inteligencia + valor)

  protected def cambiarTodoEn(valor: Int)(stat: Stat) = (cambiarFuerzaEn(valor)_ compose cambiarHpEn(valor)_ compose cambiarInteligenciaEn(valor)_ compose cambiarVelocidadEn(valor)_)(stat)
}