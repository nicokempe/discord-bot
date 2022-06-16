<template>

<div class="sidebar">
    <ul class="sidebar-elements">
        <NavItem 
            v-if="Modules.dashboard"
            text="Dashboard"
            icon="fas fa-home"
            path="/dashboard"
        />
        <NavItem 
            v-if="Modules.update"
            text="Update"
            icon="fas fa-download"
            path="/update"
        />
        <NavItem 
            v-if="Modules.statistics"
            text="Statistics"
            icon="fas fa-chart-column"
            path="/statistics"
        />
        <NavItem 
            v-if="Modules.applications"
            text="Applications"
            icon="fas fa-book"
            path="/applications"
        />
        <NavItem 
            v-if="Modules.backups"
            text="Backups"
            icon="fas fa-hard-drive"
            path="/backups"
        />
        <NavItem 
            v-if="Modules.chatfilter"
            text="Chat Filter"
            icon="fas fa-comment"
            path="/chat-filter"
        />
        <NavItem 
            v-if="Modules.customcommands"
            text="Custom Commands"
            icon="fas fa-terminal"
            path="/custom-commands"
        />
        <NavItem 
            v-if="Modules.giveaways"
            text="Giveaways"
            icon="fas fa-handshake"
            path="/giveaways"
        />
        <NavItem 
            v-if="Modules.maintenance"
            text="Maintenance"
            icon="fas fa-user-slash"
            path="/maintenance"
        />
        <NavItem 
            v-if="Modules.messages"
            text="Messages"
            icon="fas fa-message"
            path="/messages"
        />
        <NavItem 
            v-if="Modules.namefilter"
            text="Name Filter"
            icon="fas fa-filter"
            path="/name-filter"
        />
        <NavItem 
            v-if="Modules.permissions"
            text="Permissions"
            icon="fas fa-user-check"
            path="/permissions"
        />
        <NavItem 
            v-if="Modules.tickets"
            text="Tickets"
            icon="fas fa-ticket"
            path="/tickets"
        />
        <NavItem 
            v-if="Modules.welcomes"
            text="Welcomes"
            icon="fas fa-door-open"
            path="/welcomes"
        />
    </ul>
</div>

</template>

<script>

import NavItem from './NavItem.vue'
import NavSeperator from './NavSeperator.vue'
import NavDropdown from './NavDropdown.vue'

export default {
    name: 'Sidebar',
    components: {
        NavItem,
        NavSeperator,
        NavDropdown
    },
    data() {
        return {
            Modules: 
                {
                    dashboard: true,
                    update: true,
                    statistics: true,
                    applications: true,
                    backups: true,
                    chatfilter: true,
                    customcommands: true,
                    giveaways: true,
                    maintenance: true,
                    messages: true,
                    namefilter: true,
                    permissions: true,
                    tickets: true,
                    welcomes: true,
                }
        }
    }
}

</script>