export type Persona = {
  ci?: string
  nombres?: string
  apellidos?: string
  direccion?: string
  celular?: string
  prefijoCelular?: string
}

export type Empleado = Persona & {
  email?: string,
  idEmpleado?: number
}