<script setup lang="ts">
const props = defineProps({
  requiredField: {
    required: false,
    default: true,
    type: Boolean
  }
})
</script>

<template>
<v-label class="mb-1">
  <span class="font-weight-medium mr-2">
    <slot></slot>
  </span>
  <v-chip v-if="requiredField" class="mr-2" color="error" size="small">(*)</v-chip>
  <slot name="append"></slot>
</v-label>
</template>

<style scoped lang="scss">

</style>