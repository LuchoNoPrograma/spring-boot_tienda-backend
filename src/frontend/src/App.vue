<script lang="ts" setup>
import {RouterView} from "vue-router";
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition mode="out-in" name="fade">
      <component :is="Component"/>
    </transition>
  </router-view>
</template>

