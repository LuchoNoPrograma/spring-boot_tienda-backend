const MainRoutes = {
  path: '/main',
  meta: {
    requiresAuth: true
  },
  redirect: '/main',
  component: () => import('@/layouts/full/FullLayout.vue'),
  children: [
    {
      name: 'Starter',
      path: '/',
      component: () => import('@/views/StarterPage.vue')
    },
    {
      name: 'Formulario de empleado',
      path: '/empleado/formulario-registro',
      component: () => import('@/views/empleado/AdmEmpleadoForm.vue')
    }
  ]
};

export default MainRoutes;
