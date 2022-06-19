<template>
  <div class="home">
    <p>If the redirection wont work please contact the support on the Discord server this panel belongs to.</p>
  </div>
</template>

<script>

import router from '@/router';
import { onMounted } from 'vue'

const signedIn = false;
const installFinished = true;
const installStepAuthentication = true;
const installStepLanguages = true;
const installStepModules = true;

const dashboardPath = '/dashboard';
const installAuthViewPath = '/install/authentication';
const installLangSelectViewPath = '/install/language-selection';
const installModuleSelectViewPath = '/install/module-selection';
const signInViewPath = '/sign-in';
const forbiddenViewPath = '/403';

export default {
  name: 'Landing',
  setup () {
    onMounted(() => {
      if(installFinished) {
        if(signedIn) {
          router.push(dashboardPath)
        } else {
          router.push(signInViewPath);
        }
      } else if(installStepAuthentication) {
        router.push(installAuthViewPath);
      } else if(installStepLanguages) {
        router.push(installLangSelectViewPath);
      } else if(installStepModules) {
        router.push(installModuleSelectViewPath);
      } else {
        router.push(forbiddenViewPath);
      }
    })
  }
}

</script>
