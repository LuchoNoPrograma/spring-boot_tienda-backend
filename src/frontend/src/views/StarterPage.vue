<script lang="ts" setup>
import {inject, ref} from 'vue';

import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue';
import UiParentCard from '@/components/shared/UiParentCard.vue';

const swal: any = inject('$swal')
const page = ref({title: 'Sistema de gestión de tienda'});
const breadcrumbs = ref([
  {
    title: 'Inicio',
    disabled: true,
    href: '#'
  }
]);

const displayAlert = async () => {
  swal.fire({
    icon: 'success',
    title: 'Alert',
    text: 'Hello World!'
  })
};

</script>

<template>
  <BaseBreadcrumb :breadcrumbs="breadcrumbs" :title="page.title"></BaseBreadcrumb>
  <v-row>
    <v-col cols="12" md="12">
      <UiParentCard title="Simple Title">
        <v-btn color="primary" @click="displayAlert">Test</v-btn>
      </UiParentCard>
    </v-col>
  </v-row>
</template>
