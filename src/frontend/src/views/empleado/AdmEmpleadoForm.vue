<script lang="ts" setup>
import {ref} from 'vue';

import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue';
import UiParentCard from '@/components/shared/UiParentCard.vue';
import {useAxios} from "@/composables/useAxios";
import type {RestCountryType} from "@/types/RestCountryType";
import type {Empleado} from "@/types/entity";
import FormLabel from "@/components/shared/FormLabel.vue";

const {loading, error, data, execute} = useAxios();
const page = ref({title: 'Administración de empleados'});
const breadcrumbs = ref([
  {
    title: 'Formulario de registro',
    disabled: true,
    href: '#'
  }
]);

const empleado = ref<Empleado>({} as Empleado);
const listaRestCountry = ref<RestCountryType[]>([]);

execute<RestCountryType[]>('https://restcountries.com/v3.1/all?fields=name,idd,flag,flags', {method: 'GET'},
  (data) => {
    let listaFiltrada = data.filter(item => item.name.common && item.idd.root);
    listaRestCountry.value = listaFiltrada.sort((a, b) => {
      const nameA = a.name.common.toUpperCase();
      const nameB = b.name.common.toUpperCase();
      if (nameA < nameB) {
        return -1;
      }
      if (nameA > nameB) {
        return 1;
      }
      return 0;
    });
  });
</script>

<template>
  <div>
    <base-breadcrumb :breadcrumbs="breadcrumbs" :title="page.title"></base-breadcrumb>
    <v-row>
      <v-col cols="12" md="12">
        <ui-parent-card title="Formulario de registro de nuevo empleado">
          {{ empleado }}
          <v-form>
            <v-row>
              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>Nombres:</form-label>
                <v-text-field v-model="empleado.nombres" placeholder="--ESCRIBA--"></v-text-field>
              </v-col>

              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>Apellidos:</form-label>
                <v-text-field v-model="empleado.apellidos" placeholder="--ESCRIBA--"></v-text-field>
              </v-col>

              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>CI:</form-label>
                <v-text-field v-model="empleado.ci" placeholder="--ESCRIBA--"></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>Prefijo Celular:</form-label>
                <v-autocomplete v-model="empleado.prefijoCelular"
                                :item-title="(item: RestCountryType) => item.name.common"
                                :item-value="(item: RestCountryType) => `${item.idd.root}${item.idd?.suffixes ? item.idd?.suffixes : [0]}`"
                                :items="listaRestCountry"
                                placeholder="--SELECCIONE--"
                >
                  <template v-slot:item="{ props , item}">
                    <v-list-item class="d-flex" v-bind="props">
                      <span class="mr-2">{{ item.raw.flag }}</span>
                      <span class="font-weight-medium">({{item.raw.idd.root}}{{item.raw.idd?.suffixes ? item.raw.idd?.suffixes[0] : ''}})</span>
                    </v-list-item>
                  </template>
                  <template v-slot:selection="{ item, index }">
                    <span class="mr-2">{{ item.raw.flag }}</span>
                    <span class="mr-1">{{ item.raw.name.common}}</span>
                    <span class="font-weight-medium">({{item.raw.idd.root}}{{item.raw.idd?.suffixes ? item.raw.idd?.suffixes[0] : ''}})</span>
                  </template>
                </v-autocomplete>
              </v-col>

              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>N° Celular:</form-label>
                <v-text-field v-model="empleado.celular" placeholder="--ESCRIBA--"></v-text-field>
              </v-col>

              <v-col cols="12" lg="4" md="4" sm="12">
                <form-label>Dirección:</form-label>
                <v-textarea v-model="empleado.direccion" placeholder="--ESCRIBA--" rows="1"></v-textarea>
              </v-col>
            </v-row>
          </v-form>
        </ui-parent-card>
      </v-col>
    </v-row>
  </div>
</template>
