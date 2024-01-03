<script lang="ts" setup>
import BaseBreadcrumb from "@/components/shared/BaseBreadcrumb.vue";
import type {Cargo} from "@/types/entity";
import {computed, ref} from "vue";
import {useAxios} from "@/composables/useAxios";
import FormLabel from "@/components/shared/FormLabel.vue";
import DialogToolbar from "@/components/shared/DialogToolbar.vue";
import useVuelidate from "@vuelidate/core";
import {fieldRequired} from "@/utils/helpers/vuelidate-rules";
import {alertCreated, alertDeleted, alertError, alertUpdated} from "@/utils/helpers/sweetalert-dialog";
import type {crudFormType} from "@/types/FormTypes";
import Swal from "sweetalert2";

const breadcrumbs = [{
  title: 'Cargos',
  disabled: true,
  href: '#'
}]

const headers = [{
  title: 'Nombre',
  align: 'center',
  key: 'nombreCargo'
}, {
  title: 'Descripción',
  key: 'descripcion'
}, {
  title: 'Operaciones',
  align: 'center',
  key: 'idCargo'
}]

const listaCargo = ref<Cargo[]>([])

const {loading, error, execute} = useAxios();
const fetchListaCargo = async () => {
  await execute<Cargo[]>('/api/cargo', {method: 'GET'}, (data) => listaCargo.value = data);
}
//===== DIALOG FORM =====
const dialog = ref(false);
const cargo = ref<Cargo>({});
const crudForm = ref<crudFormType>('create');

const rulesSchema = computed(() => ({
  nombreCargo: {fieldRequired},
  descripcion: {fieldRequired}
}))

//@ts-ignore ... i dont understand why vuelidate doesn't apply a type to state with optional fields
const rules = useVuelidate(rulesSchema, cargo);

const closeDialog = () => dialog.value = false

const submitForm = async () => {
  await execute<Cargo>('/api/cargo', {
      method: crudForm.value == 'create' ? 'POST' : 'PUT',
      data: cargo.value
    },
    (data, status) => {
      if (status == 201 || status == 200) {
        crudForm.value == 'create' ? alertCreated() : alertUpdated();
        cargo.value = {};
        rules.value.$reset();
        closeDialog();
      }
    }, (errorCatch) => {
      alertError(errorCatch)
    });

  await fetchListaCargo();
}

const openDialogCreate = () => {
  crudForm.value = 'create';
  cargo.value = {};
  dialog.value = true;
}
const openDialogUpdate = (data: Cargo) => {
  crudForm.value = 'update';
  cargo.value = Object.assign({}, data);
  dialog.value = true;
  rules.value.$reset();
}

const submitDeleteForm = async (cargo: Cargo) => {
  let cargoCopy = Object.assign({}, cargo);
  return Swal.fire({
    icon: 'warning',
    title: 'Advertencia!',
    html: `Esta seguro de eliminar el cargo <b>${cargoCopy.nombreCargo}</b>?`,
    showCancelButton: true,
    cancelButtonText: 'No, cancelar',
    confirmButtonText: 'Si, eliminar',
    allowOutsideClick: () => !Swal.isLoading(),
    preConfirm: () => {
      execute<Cargo>('/api/cargo/' + cargoCopy.idCargo, {method: 'DELETE'}, (data, status) => {
        if (status == 204) {
          fetchListaCargo();
          alertDeleted();
        }
      }, (errorCatch) => {
        alertError(errorCatch);
      });
    }
  }).then(async (result) => {
    if (result.isConfirmed) {
      return Swal.fire({
        icon: 'success',
        title: 'Operación exitosa!',
        html: `El cargo ${cargoCopy.nombreCargo} ha sido eliminado correctamente!`,
      })
    }
  })
}

fetchListaCargo();
</script>

<template>
  <div>
    <base-breadcrumb :breadcrumbs="breadcrumbs" title="Lista de cargos"></base-breadcrumb>
    <v-data-table :headers="headers" :items="listaCargo">
      <template v-slot:top>
        <v-toolbar>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="600px">
            <template v-slot:activator="{props}">
              <v-btn color="success" v-bind="props" variant="elevated" @click="openDialogCreate">
                Registrar
                <file-plus-icon></file-plus-icon>
              </v-btn>
            </template>
            <v-card>
              <v-card-item class="bg-primary pb-2">
                <dialog-toolbar @closeDialog="closeDialog">
                  <template v-slot:title>{{ crudForm == 'create' ? 'Registrar nuevo cargo' : 'Editar cargo: '+cargo.nombreCargo }}</template>
                  <template v-slot:subtitle>Rellene los datos en el siguiente formulario.</template>
                </dialog-toolbar>
              </v-card-item>

              <v-card-text>
                <v-container>
                  <v-row>
                    <input v-model="cargo.idCargo" type="hidden">
                    <v-col cols="12" md="4" sm="12">
                      <form-label>Nombre:</form-label>
                      <v-text-field v-model="cargo.nombreCargo"
                                    :error-messages="rules.nombreCargo.$errors.map(e => e.$message as string)"
                                    counter="55" maxlength=55
                                    persistent-counter
                                    @blur="rules.nombreCargo.$touch()"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" md="8" sm="12">
                      <form-label>Descripción:</form-label>
                      <v-textarea v-model="cargo.descripcion"
                                  :error-messages="rules.descripcion.$errors.map(e => e.$message as string)"
                                  counter="300" maxlength="300"
                                  persistent-counter rows="1"
                                  @input="rules.descripcion.$touch()"></v-textarea>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions class="d-flex justify-end">
                <v-btn append-icon="mdi-send" color="primary" variant="elevated" @click="submitForm()">
                  Enviar
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.idCargo="{item}">
        <v-btn :icon="true" class="mr-2" color="primary" size="small" @click="openDialogUpdate(item)">
          <edit-icon></edit-icon>
          <v-tooltip activator="parent">Editar</v-tooltip>
        </v-btn>
        <v-btn :icon="true" color="error" size="small" @click="submitDeleteForm(item)">
          <trash-icon></trash-icon>
          <v-tooltip activator="parent">Eliminar</v-tooltip>
        </v-btn>
      </template>
    </v-data-table>
  </div>
</template>

<style lang="scss" scoped>

</style>