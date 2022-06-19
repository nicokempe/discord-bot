import { createRouter, createWebHistory } from 'vue-router'
import Landing from '../views/Landing.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Landing
  },
  // installation pages
  {
    path: '/install/authentication',
    name: 'BotAuthentication',
    component: function () { return import('../views/install/BotAuthentication.vue') }
  },
  {
    path: '/install/language-selection',
    name: 'LanguageSelection',
    component: function () { return import('../views/install/LanguageSelection.vue') }
  },
  {
    path: '/install/module-selection',
    name: 'ModuleSelection',
    component: function () { return import('../views/install/ModuleSelection.vue') }
  },
  {
    path: '/install/completed',
    name: 'InstallationCompleted',
    component: function () { return import('../views/install/InstallationCompleted.vue') }
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
    path: '/2fa-setup',
    name: '2FASetup',
    component: function () { return import('../views/auth/2FASetup.vue') }
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
  // default panel views
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: function () { return import('../views/panel/Dashboard.vue') }
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: function () { return import('../views/panel/Statistics.vue') }
  },
  {
    path: '/update',
    name: 'Updater',
    component: function () { return import('../views/panel/modules/Updater.vue') }
  },
  // modular panel views
  {
    path: '/applications',
    name: 'Applications',
    component: function () { return import('../views/panel/modules/applications/Overview.vue') }
  },
  {
    path: '/backups',
    name: 'Backups',
    component: function () { return import('../views/panel/modules/backups/Overview.vue') }
  },
  {
    path: '/chat-filter',
    name: 'ChatFilter',
    component: function () { return import('../views/panel/modules/chat-filter/Overview.vue') }
  },
  {
    path: '/custom-commands',
    name: 'CustomCommands',
    component: function () { return import('../views/panel/modules/custom-commands/Overview.vue') }
  },
  {
    path: '/giveaways',
    name: 'Giveaways',
    component: function () { return import('../views/panel/modules/giveaways/Overview.vue') }
  },
  {
    path: '/maintenance',
    name: 'Maintenance',
    component: function () { return import('../views/panel/modules/maintenance/Overview.vue') }
  },
  {
    path: '/messages',
    name: 'Messages',
    component: function () { return import('../views/panel/modules/messages/Overview.vue') }
  },
  {
    path: '/name-filter',
    name: 'NameFilter',
    component: function () { return import('../views/panel/modules/name-filter/Overview.vue') }
  },
  {
    path: '/permissions',
    name: 'Permissions',
    component: function () { return import('../views/panel/modules/permissions/Overview.vue') }
  },
  {
    path: '/tickets',
    name: 'Tickets',
    component: function () { return import('../views/panel/modules/tickets/Overview.vue') }
  },
  {
    path: '/welcomes',
    name: 'Welcomes',
    component: function () { return import('../views/panel/modules/welcomes/Overview.vue') }
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
