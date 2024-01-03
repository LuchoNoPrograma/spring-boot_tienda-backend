<script lang="ts" setup>
import {RouterView} from "vue-router";
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition mode="out-in" name="fade">
      <component :is="Component"></component>
    </transition>
  </router-view>
</template>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>