import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  // error pages
  {
    path: '/400',
    name: 'BadRequest',
    component: function () { return import('../views/errors/BadRequest.vue') }
  },
  {
    path: '/401',
    name: 'Unauthorized',
    component: function () { return import('../views/errors/Unauthorized.vue') }
  },
  {
    path: '/403',
    name: 'Forbidden',
    component: function () { return import('../views/errors/Forbidden.vue') }
  },
  {
    path: '/404',
    name: 'NotFound',
    component: function () { return import('../views/errors/NotFound.vue') }
  },
  // legal pages
  {
    path: '/credits',
    name: 'Credits',
    component: function () { return import('../views/legal/Credits.vue') }
  },
  {
    path: '/imprint',
    name: 'Imprint',
    component: function () { return import('../views/legal/Imprint.vue') }
  },
  {
    path: '/privacy-policy',
    name: 'PrivacyPolicy',
    component: function () { return import('../views/legal/PrivacyPolicy.vue') }
  },
  {
    path: '/terms-of-service',
    name: 'TermsOfService',
    component: function () { return import('../views/legal/TermsOfService.vue') }
  },
  // authentication pages
  {
    path: '/2fa',
    name: '2FA',
    component: function () { return import('../views/auth/2FA.vue') }
  },
  {
    path: '/forget-password',
    name: 'ForgetPassword',
    component: function () { return import('../views/auth/ForgetPassword.vue') }
  },
  {
    path: '/sign-in',
    name: 'SignIn',
    component: function () { return import('../views/auth/SignIn.vue') }
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: function () { return import('../views/auth/SignUp.vue') }
  },
  // default panel views
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: function () { return import('../views/Dashboard.vue') }
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: function () { return import('../views/Statistics.vue') }
  },
  {
    path: '/update',
    name: 'Updater',
    component: function () { return import('../views/modules/Updater.vue') }
  },
  // modular panel views
  {
    path: '/applications',
    name: 'Applications',
    component: function () { return import('../views/modules/applications/Overview.vue') }
  },
  {
    path: '/backups',
    name: 'Backups',
    component: function () { return import('../views/modules/backups/Overview.vue') }
  },
  {
    path: '/chat-filter',
    name: 'ChatFilter',
    component: function () { return import('../views/modules/chat-filter/Overview.vue') }
  },
  {
    path: '/custom-commands',
    name: 'CustomCommands',
    component: function () { return import('../views/modules/custom-commands/Overview.vue') }
  },
  {
    path: '/giveaways',
    name: 'Giveaways',
    component: function () { return import('../views/modules/giveaways/Overview.vue') }
  },
  {
    path: '/maintenance',
    name: 'Maintenance',
    component: function () { return import('../views/modules/maintenance/Overview.vue') }
  },
  {
    path: '/messages',
    name: 'Messages',
    component: function () { return import('../views/modules/messages/Overview.vue') }
  },
  {
    path: '/name-filter',
    name: 'NameFilter',
    component: function () { return import('../views/modules/name-filter/Overview.vue') }
  },
  {
    path: '/permissions',
    name: 'Permissions',
    component: function () { return import('../views/modules/permissions/Overview.vue') }
  },
  {
    path: '/tickets',
    name: 'Tickets',
    component: function () { return import('../views/modules/tickets/Overview.vue') }
  },
  {
    path: '/welcomes',
    name: 'Welcomes',
    component: function () { return import('../views/modules/welcomes/Overview.vue') }
  },
  /* {
    path: '/about',
    name: 'about',
    route level code-splitting
    this generates a separate chunk (about.[hash].js) for this route
    which is lazy-loaded when the route is visited.
    component: function () {
      return import(webpackChunkName: "about" oder '../views/legal/Imprint.vue')
    }
  } */
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
