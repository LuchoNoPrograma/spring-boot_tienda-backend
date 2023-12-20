<script setup lang="ts">
import { ref } from 'vue';

import BaseBreadcrumb from '@/components/shared/BaseBreadcrumb.vue';
import UiParentCard from '@/components/shared/UiParentCard.vue';

const page = ref({ title: 'Sistema de gestión de tienda' });
const breadcrumbs = ref([
    {
        title: 'Inicio',
        disabled: true,
        href: '#'
    }
]);

</script>

<template>
    <BaseBreadcrumb :title="page.title" :breadcrumbs="breadcrumbs"></BaseBreadcrumb>
    <v-row>
        <v-col cols="12" md="12">
            <UiParentCard title="Simple Title"> test </UiParentCard>
        </v-col>
    </v-row>
</template>
