<script setup lang="ts">
const emits = defineEmits(['closeDialog']);

const closeDialog = () => emits('closeDialog');
</script>

<template>
  <div class="d-flex justify-space-between flex-nowrap align-center">
    <div class="flex-shrink-1">
      <v-card-title class="text-wrap text-h4">
        <slot name="title"></slot>
      </v-card-title>
      <v-card-subtitle class="text-wrap">
        <slot name="subtitle"></slot>
      </v-card-subtitle>
    </div>

    <div class="ml-auto flex-shrink-0">
      <v-btn icon variant="text" @click="closeDialog">
        <x-icon width="20"></x-icon>
      </v-btn>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>