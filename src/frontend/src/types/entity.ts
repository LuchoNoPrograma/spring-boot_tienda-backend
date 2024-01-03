type AuditoriaRevision = {
  fechaRegistro?: Date | string
}

export type Persona = {
  ci?: string
  nombres?: string
  apellidos?: string
  direccion?: string
  celular?: string
  prefijoCelular?: string
}

export type Empleado = Persona & AuditoriaRevision & {
  email?: string,
  idEmpleado?: number
}

export type Cargo = AuditoriaRevision & {
 idCargo?: number,
 nombreCargo?: string,
 descripcion?: string
}