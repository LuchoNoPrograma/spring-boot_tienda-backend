import Swal from "sweetalert2";

export async function alertError(cause: string) {
  await Swal.fire({
    icon: 'error',
    title: 'Error...',
    html: 'Ocurrió un error al enviar el formulario, ' +
      '<br><span class="font-weight-regular">Motivo: </span><br>' + cause
  })
}

export async function alertCreated() {
  await Swal.fire({
    icon: 'success',
    title: 'Operación exitosa!',
    html: 'Se ha realizado el registro correctamente!'
  })
}

export async function alertUpdated() {
  await Swal.fire({
    icon: 'success',
    title: 'Operación exitosa!',
    html: 'Se ha actualizado el registro correctamente!'
  })
}

export async function alertDeleted() {
  await Swal.fire({
    icon: 'success',
    title: 'Operación exitosa!',
    html: 'Se ha eliminado el registro correctamente!'
  })
}